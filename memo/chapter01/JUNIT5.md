# 1부 JUNIT 5

###1-1 소개

* JUnit Platform: 테스트를 실행하는 런처 제공, 실제로 메인 메소드가 아닌데 실행이 가능한 이유는 IDE에서 JUnit Platform을 이용하여 테스트 메소드를 실행 시키기 때문

* Jupiter: TestEngine API 구현체. JUnit5를 재공

* Vintage: Junit4와 3을 지원하는 TestEngine 구현체

###1-2 시작하기

* JUnit5는 스프링 부트 2.2부터 spring-boot-starter-test에 JUnit5가 기본으로 제공된다.

* maven 탭(intellij)에서 디펜던시 확인을 할 수 있다.

* JUnit4에서는 public 클래스와 public 메서드만 실행이 가능했지만 JUnit5부터는 public으로 지정 하지 않아도 된다.

> * BeforeAll: 테스트가 모두 실행이 되기 전에 딱 한번만 호출이 되고, static method로 사용을 해야 한다. private는 사용이안된다. default가능. return tpye이 지정되면 안된다.
> * AfterAll: 모든 테스트가 실행 된 이후에 딱 한번마 실행이 된다. 마찬가지로 static void만 가능하다.
> * BeforeEach: 각각의 테스트를 실행하기 이전에 실행이 된다. static일 필요는 없다.
> * AfterEach: 각각의 테스트를 실행하고 나서 실행이 된다.
> * Disable: 테스트를 실행하지 않는다.