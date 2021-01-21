# 회원 리포지토리 테스트 케이스 작성

* 테스트 방법
    - main method 통해 실행
    - 웹 애플리케이션의 컨트롤러를 통해서 해당 기능을 실행
        + 위 두가지 방법은 준비하고 실행하는데 오래 걸리고, 반복 실행하기 어렵고 여러 테스트를 한번에 실행하기 어렵다는 단점이 존재
    - Junit 프레임 워크 테스트


**회원 리포지토리 메모리 구현체 테스트**

**src/test/java** 하위 폴더에 생성한다.

```java
/**
 * 순서는 보장이 되지 않는다.
 * @AfterEach : 테스트 끝나자마자 실행되어 클리어 해줌
 * TDD : 테스트를 먼저 만들고 구현 클래스를 만들어서 실행
 */
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }


    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);

        //Assertions.assertEquals(member, null);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }
    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);

    }
}
```


- @AfterEach
    + 각 테스트 메서드가 종료되자마자 실행되는 메서드 (객체 클리어 용으로 사용)
    + 테스트 방식은 순서가 보장되지 않기 때문에 사용됨
    + 한번에 여러 테스트 진행시 메모리 DB에 직전 테스트의 결과가 남을 수 있다.
    + 이렇게 되면 다음 이전 테스트 때문에 다음 테스트 실패 가능성 높다
    + 테스트는 각각 독립적으로 실행되어야 한다. 의존관계가 있는 것은 좋은 테스트가 아니다.


- AssertThat
    + Junit 4.4부터 assertThat 메서드가 추가
    + 객체 비교 테스트


- Assertions.assertThar(기대값).isEqualTo(비교값)
    + static import 처리가능


- Save method
    + Member 객체 생성
    + name 지정
    + repository member 저장
    + repository member Id 존재유무 검색
    + assertThat 사용하여 member / result(기대값)  테스트


- findByName()
    + Save 기능과 동일한 방법


- findAll()
    + 현재 repository 저장된 객체 개수 테스트

**회원 서비스 개발**
```java
class MemberServiceTest {

    MemberService memberService;
    // 객체가 다른 2개다 ( 굳이 2개를 쓸 필요는 없다 -> 하나를 쓰는게 좋다)
    MemoryMemberRepository memberRepository;

    // 다른 객체가 되는것이 아닌 하나의 객체로 테스트 할 수 있도록 만듬.
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEa(){
        memberRepository.clearStore();
    }

    // 테스트 메소드는 한글로도 적는다.
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("Spring");

        Member member2 = new Member();
        member2.setName("Spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /*try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        //then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
```

- 기존에는 회원 서비스가 메모리 회원 리포지토리를 직접 생성하게 했다.
  ```java
  private final MemberRepository memberRepository = new MemoryMemberRepository();
  ```
- 회원 리포지토리 코드가 **회원 서비스 코드를 DI 가능하게 변경한다.**
  ```java
    private final MemberRepository memberRepository;

    // 생성자 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
  ```
- @BeforeEach
    + 각 테스트 실행 전에 호출된다. 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어준다.

- 테스트 코드 작성 방법
    + given / when / then 방식의 코딩이 좋은 방식
    + try ~ catch 이용해서 예외처리를 할것이 아니라 assertThrows 를 이용하여 예외를 처리
 