# UFO_FI_BE 코드 컨벤션

# Controller

<details>
  
```java
@Tag(name = "Mypage API", description = "마이페이지 계좌 API")
public interface MyPageAccountApiSpec {

    @Operation(summary = "계좌 조회 API", description = "내 계좌 정보를 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/account")
    ResponseEntity<ResponseBody<AccountReadRes>> readAccount(
        @RequestParam Long userId
    );
}

//----------------------------------------------------------------------------------------------

@RestController
@RequiredArgsConstructor
public class MyPageAccountController implements MyPageAccountApiSpec {
    private final UserService userService;

    @Override
    public ResponseEntity<ResponseBody<AccountReadRes>> readAccount(
            Long userId
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.readUserAccount(userId)));
    }
}
```
1. 각 API는 각 `HTTP METHOD`에 따른다.
   
2. 각 API의 DTO는 어떤 API의 데이터인지 명확히 인식할 수 있어야 한다.(단, Read는 그냥 리소스를 나타낸다.)
   
    => 유저를 삭제하는 DTO `UserDeleteReq`

    => 유저들을 찾아오는 DTO `UsersReadReq`

3. rest API의 철학 상 요청을 받으면 반드시 응답을 해야한다.

4. Controller는 반드시! 어떠한 로직도 들어가면 안된다.고 생각한다. 최대한 심플하게 해야한다.
   
5. 예외처리는 service 단에서 해주자.

6. Controller는 클라이언트에게 데이터를 잘 나타내주기만 하면된다.

</details>

---

# Service

<details>

```java
@Service
@RequiredArgsConstructor
public class DefaultAddonService {
    private final DefaultAddonRepository defaultAddonRepository;

    public DefaultAddonsResponse findAll(PageRequest pageRequest) {
        return getAddonsResponse(pageRequest);
    }

    @Transactional
    public DefaultAddonCreateResponse save(DefaultAddonCreateRequest defaultAddonCreateRequest, Role role) {
        if(role == Role.USER) throw new GlobalException(ErrorCode.FORBIDDEN);
        DefaultAddon savedDefaultAddon = defaultAddonRepository.save(getDefaultAddon(defaultAddonCreateRequest));
        return getDefaultAddonCreateResponse(savedDefaultAddon.getId());
    }

    public DefaultAddonResponse findById(Long addonId) {
        return getDefaultAddonById(addonId);
    }

    private DefaultAddonsResponse getAddonsResponse(PageRequest pageRequest) {
        Page<DefaultAddon> defaultAddons = defaultAddonRepository.findAll(pageRequest);
        return DefaultAddonsResponse.from(defaultAddons);
    }

    private DefaultAddon getDefaultAddon(DefaultAddonCreateRequest defaultAddonCreateRequest) {
        return DefaultAddon.from(defaultAddonCreateRequest);
    }

    private DefaultAddonCreateResponse getDefaultAddonCreateResponse(Long id){
        return DefaultAddonCreateResponse.from(id);
    }

    private DefaultAddonResponse getDefaultAddonById(Long addonId) {
        DefaultAddon defaultAddon = defaultAddonRepository.findById(addonId)
                .orElseThrow(() -> new GlobalException(ErrorCode.DB_ERROR));
        return DefaultAddonResponse.from(defaultAddon);
    }
```
1. Service는 일종의 facade로 많은 의존성이 모이는 곳이다.
   
2. 최대한 매서드의 의미를 담아 네이밍을 잘해야한다.(이래야 덜 헷갈림)
   
3. 나는 여러 매핑(dto-> entity, entity -> dto)는 최대한 다른 메서드로 빼주는 편이다.(네이밍 잘할 것)

4. 단일 조회 로직에서는 @Transactional(readOnly=true)를 빼준다.
   
    => 영속성 추적을 위한 오버헤드가 덜 들기 때문이다.

6. 메서드 줄 수는 굳이 짧게 안해도된다. 나는 강박이 있어 저렇게 한 것이다.

</details>

---

# Repository

<details>

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserQueryDsl{
    
    // 기본 CRUD는 JpaRepository에서 제공
    
    // 커스텀 쿼리 메서드
    List<User> findByEmail(String email);
    List<User> findByEmailAndActive(String email, boolean active);
    List<User> findByAgeGreaterThan(int age);
    List<User> findByNameContaining(String name);
    
    boolean existsByEmail(String email);
    long countByActive(boolean active);
    
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmailCustom(String email);
}

//---------------------------------------------------------------------------------------------------

public interface UserQueryDsl{
	List<User> findAllByUserIdAndUsername(Long userId, String username);
	User findUserByUserIdAndUsername(Long userId, String username);
}

//--------------------------------------------------------------------------------------------------

@Repository
public class UserQueryDslImpl implements UserQueryDsl {

    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;

    public UserQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<User> findAllByUserIdAndUsername(Long userId, String username) {
        return queryFactory
                .selectFrom(user)
                .where(user.id.eq(userId)
                    .and(user.username.eq(username)))
                .fetch();
    }

    @Override
    public User findUserByUserIdAndUsername(Long userId, String username) {
        return queryFactory
                .selectFrom(user)
                .where(user.id.eq(userId)
                    .and(user.username.eq(username)))
                .fetchOne();
    }
}

```
1. 위와 같이 repository를 생성한다.

2. 기본 JpaRepository의 메서드를 사용할 경우 그냥 사용해준다.

3. 쿼리 DSL 같은 동적 쿼리를 구현 시 인터페이스를 만들고, 구현 후 해당 도메인 리포지토리에 상속 해준다.
  
</details>

---

# Entity

<details>
  
</details>

---

# Exception

<details>

```java
public interface ErrorCode {
    HttpStatus getStatus();
    String getMessage();
}

//------------------------------------------------------------------------------------

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NO_USER(HttpStatus.NOT_FOUND, "식별되지 않은 사용자입니다."),
    NO_PHOTO(HttpStatus.INTERNAL_SERVER_ERROR, "사용가능한 프로필 사진이 없습니다."),
    NO_NICKNAME(HttpStatus.INTERNAL_SERVER_ERROR, "사용가능한 닉네임 형용사가 없습니다."),
    RANDOM_NUMBER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "랜덤 값을 생성할 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
```
1. 예외 코드는 각 도메인 별 Exception에 추가한다.

2. ErrorCode를 implements 받아야 한다.

3. `if(role == Role.USER) throw new GlobalException(ErrorCode.FORBIDDEN);` 서비스 단에서 이런 식으로 throw 할 수 있다.


</details>

---

# DTO

<details>

```java
@Getter
@NoArgsConstructor
public class TradePostCreateReq {

    @Schema(description = "판매글 제목", example = "급처분합니다.")  
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 15, message = "제목은 1~15자 이내여야 합니다.")
    private String title;

    @Schema(description = "판매 가격", example = "120")
    @Min(value = 1, message = "가격은 1ZET 이상이여야 됩니다.")
    private Integer price;

    @Schema(description = "판매할 데이터 용량", example = "10")
    @Min(value = 1, message = "용량은 1GB 이상이어야 됩니다.")
    private Integer sellMobileDataCapacityGb;
}
```
  
</details>
