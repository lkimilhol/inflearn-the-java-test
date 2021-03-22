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

###1-3 테스트 이름 표시하기

* DisplayNameGeneration 전략을 통해 이름 표기가 가능 (모든 메소드에 적용 가능) 
* DisplayName 으로 이름 표기 가능. 직접 이름을 넣어 줄 수가 있다.

###1-4 Assertion

* assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 DRAFT여야 한다"); 식으로 메시지를 줄 수 있다. 메시지를 supplier 람다를 사용 할 수도 있다.
* 위 메서드의 안에 expected를 넣어주고 실제 나오는 값을 넣어주면 된다.

* 식으로 가능하다는 이야기 
```
assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
            @Override
            public String get() {
                return "스터디를 처음 만들면 DRAFT여야 한다";
            }
        });
```
* 람다식으로 만들어 주면 문자열을 만드는 연산을 하지 않는다. 최대한 필요한 시점에 한다는 이야기. (실패했을때만 실행한다.)
* 만약 실패하는 테스트 케이스가 있는 경우 그 아래에 있는 테스트케이스는 실행되지 않는다. 하지만 assertAll을 사용하면 모두 실행을 한다.
* assertTimeout의 경우 Thread.sleep에서 준 시간만큼 기다리게 되는데, assertTimeoutPreemptively를 실행하면 설정 된 시간이 넘어가면 즉각 종료가 된다.
* ThreadLocal을 사용하는 경우에는 예상치 못한 결과가 나올 수 있다. ThreadLocal은 스레드 공유가 되지 않는다. (Spring 트랜잭션이 기본적으로 threadLocal을 기본 전략으로 사용함)
* **즉 threadLocal을 사용해서 실제 db에 반영이 될 수 있다(롤백이 되지 않기 때문에)**. 트랜잭션에 관련된 스레드와 별개의 스레드로 사용하기 때문.






