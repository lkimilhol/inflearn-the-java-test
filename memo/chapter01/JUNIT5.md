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

###1-5 조건에 따라 테스트 실행하기

환경 변수 등의 데이터를 가져오고, assumeTrue 라는 메서드를 이용하여 테스트를 비교하여 이후의 테스트를 실행할지 말지 결정 할 수가 있다.

```
assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
    System.out.println("test");
});
```
이런식으로 각 환경변수에 따른 설정을 비교하고 테스트를 실행할지 말지도 가능하다.

또한 @EnabledOnOs({OS.MAC, OS.LINUX})의 애노테이션을 사용하여 특정 OS에서 테스트를 하는것이 가능하다.

@EnabledJre({JRE.JAVA_8})의 애노테이션을 사용하여 특정 자바 버전에서만 실행을 하도록 하는 것이 가능하다.


###1-6 태깅과 필터링

우리는 태깅을 통해 테스트케이스들을 그루핑 할 수가 있는데 @Tag 애노테이션을 사용하면 된다.

@Tag("fast") 등의 애노테이션을 통해 테스트케이스가 빠름을 표현 할 수도 있으며, @Tag("slow") 등의 애노테이션을 통해 로컬이 아닌 다른 서버에서 테스트케이스를 실행 시키고 싶을 때 테스트를 실행하도록 할 수도 있다.

태깅에 따른 테스트케이스 실행을 위해서는 intelliJ의 edit Configuration에서 Test kind를 Tags로 바꾸고 fast를 입력하면 fast가 태깅 된 테스트케이스만 테스트가 가능하다.

이후 빌드툴을 이용해 테스트케이스 실행에 대한 전략을 결정 할 수가 있다(fast를 실행할지 slow를 실행할지).

###1-6 커스텀 태그

자바에서 제공하는 메타 애노테이션을 사용하여 새로운 애노테이션을 생성 할 수가 있다.

```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("fast")
public @interface FastTest {
}
```

이 애노테이션은 메소드에 붙일 수 있으며 런타임의 유지범위를 제한한다. 또한 @Test 애노테이션과 @Tag("fast")의 애노테이션을 합한것과 같은 조합 된 애노테이션을 생성한 것이다.

이후 @FastTest 애노테이션을 통해 @Test와 @Tag("fast")라는 애노테이션을 동시에 사용한 효과가 나게 된다.

@Tag("fast")의 오타가 발생되어 테스트가 실행되지 않는 오류를 벗어날 수 있다.

###1-7 JUnit 5 테스트 반복하기 1부

@RepeatedTest 애노테이션을 사용해 특정 테스트케이스의 반복이 가능하다.

또한 테스트 케이스의 매개변수로 RepetitionInfo를 받아서 현재 테스트 중인 횟수와 전체 횟수 등을 쉽게 가져올 수 있다.

애노테이션을 통해 테스트케이스의 이름과 반복 횟수도 보여 줄 수 있는데, 

@DisplayName("스터디 만들기")
@RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")

식으로 테스트케이스 위에 애노테이션을 붙이면

스터디 만들기, 1/10  
스터디 만들기, 2/10  
스터디 만들기, 3/10  
스터디 만들기, 4/10  
스터디 만들기, 5/10  

식으로 보기 쉬운 테스트케이스 이름을 지어 줄 수 있다.


만약 파라미터를 타양하게 주어 테스트 케이스를 진행하려고 한다면 @ParameterizedTest 애노테이션을 통해 이가 가능하다.

Jnit5부터 기본적으로 사용 가능하며 전 버전에서는 서드 파티 라이브러리를 이용해야한다.

```
@DisplayName("스터디 만들기")
@ParameterizedTest(name = "{index} {displayName} message={0}")
@ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요,"})
void parameterizedTest(String message) {
    System.out.println(message);
}
```

총 4번의 테스트가 실행이 될텐데 첫번째는 날씨가 두번째는 많이 라는 파라메터를 가지고 테스트를 실행하게 된다.

마찬가지로 인자로 name을 지정하여 테스트 케이스의 이름을 보여 줄 수 있다.

1 스터디 만들기 message=날씨가  
2 스터디 만들기 message=많이  
3 스터디 만들기 message=추워지고  
4 스터디 만들기 message=있네요,  

이런식으로 지금 파라메터로 받은 message가 무엇인지 확인이 가능하다.

###1-8 JUnit 5 테스트 반복하기 2부

@ParameterizedTest 애노테이션에는 다양한 타입의 인자를 받아 테스트가 가능하다.

@EmptySource는 빈 문자열을 넣고 @NullSource는 null 값을 인자로 주는 것이다.

@ValueSource를 통해 특정 타입의 값을 넣어 줄 수 있는데, 타입을 암묵적으로 변환해 주는 경우가 있다.

SimpleArgumentConverter를 통해 내가 원하는 특정 타입으로 변환할수있는데, 이때는 구현체를 구현해야한다.

```
@DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest(Study study) {
        System.out.println(study.getLimit());
}
```

위 예제는 ints로 구성된 파라메터를 Study 클래스의 limit로 넘겨주는 예제이다. (Study 클래스 안에 int limit라는 멤버 변수를 가지고 있다고 생각하자)

우리는 여기서 SimpleArgumentConverter 클래스를 상속받아 컨버터를 구현함으로써 int로 넘겨진 파라메터를 변환할수 있다.

static class StudyConverter extends SimpleArgumentConverter {

```
static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            assertEquals(Study.class, aClass);
            return new Study(Integer.parseInt(o.toString()));
        }
    }
```

컨버터를 구현했다. 이제 파라메터로 어떤 타입으로 변환이 되는지 알려주어야 한다. 위 클래스의 파라메터를 수정하자.

```
@DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }
```

1 스터디 만들기 message=10  
2 스터디 만들기 message=20  
3 스터디 만들기 message=40  

위의 컨버터는 하나의 타입만 받기가 가능하다.

그렇다면 아래처럼 2가지 타입을 받기 위해서는 어떻게 해야할까?

인자로 ArgumentAccessor를 통하여 이가 가능하고 @CsvSource를 통해 여러 인자를 줄 수 있다.

즉 

```
@CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest2(ArgumentsAccessor argumentsAccessor) {}
```

식으로 구현이 가능하다.

또한 Aggregator를 사용하여 Study를 넘겨주는 식으로도 가능하다. 위의 컨버터와 비슷하다.

```
static class StudyAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
    }
}
```

사용 방법도 비슷한데 어떤 타입을 사용할지 @AggregateWith 애노테이션을 사용하면 된다.

```
@DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest2(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }
```

애그리게이터의 제약조건으로는 꼭 static inner 클래스 이거나 꼭 public 클래스 형태여야 한다.


###1-9 JUnit 5 테스트 인스턴스

Junit이 테스트를 실행할때 메서드를 실행할텐데, 결국 클래스 인스턴스를 만들어야 한다.

기본 전략은 테스트 인스턴스를 메서드가 실행될때마다 만들어 지는데 

```
class StudyTest{
    int value = 0;
    
    method1 () {value++;}
    method2 () {value++;}
    method3 () {value++;}
}
```

위의 클래스에서 value 값을 증가시키려 한다면 value값이 증가 될 거 같지만 이 값은 절대 올라가지 않는다.

클래스의 인스턴스 해시값을 보면 각기 다르다는 걸 알 수 있다. 이것이 JUnit의 기본 전략이다.

이유는 테스트간의 의존성을 없애기 위해서이다. (테스트케이스의 순서는 각기 다를 수 있다. Junit5 에서는 순서대로 실행되지만 **절대 순서가 정해져 있다고 생각하면 안된다.**)

JUnit5 에서는 이 전략을 클래스당 하나를 만들어서 실행이 가능하다. 이 경우에는 성능상 장점이 있을거고 제약이 조금 느슨해 지는 경우가 있다.

테스트 클래스에 해당 애노테이션을 붙이면 클래스 마다 생성이 되게 된다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) 

또한 클래스당 한개의 인스턴스를 만들게 된다면 @BeforeAll, @AfterAll이 static일 필요가 없다.


###1-10 JUnit 5 테스트 순서

JUnit5에서는 특정한 로직에 따라 테스트케이스의 순서가 정해지게 된다. 하지만 이 순서는 언제라도 바뀔 수 있으므로 **절대 순서에 의존하면 안된다.**

만약 단위 테스트가 제대로 작동이 된다면 서로간의 의존성이 없어야 하기 때문에 어떤 테스트케이스를 실행시켜도 모두 정상 작동이 되어야 한다.

간혹 내가 원하는 순서대로 테스트가 실행 되어야 할 때도 있을텐데, 순서를 정하는 방법을 알아보도록 하자.

테스트케이스 애노테이션으로 @TestMethodOrder(MethodOrderer.OrderAnnotation.class)를 주고 우리는 order 즉 순서를 정해주면 된다.

그리고 순서를 원하는 메서드에 @Order(1) 식으로 번호를 부여하면 되고, 낮은 값이 제일 큰 우선순위를 가진다.


###1-11 JUnit 5 junit-platform.properties

JUnit의 설정 파일을 사용하는 방법이다.

test 폴더 아래에 resource 폴더를 만들고 junit-platform.properties 파일을 생성하여 

junit.jupiter.testinstance.lifecycle.default = per_class

를 넣어주면 클래스마다 인스턴스를 생성하게 된다.

또한 확장팩 자동 감지 기능이라던가 @Disalbed 무시하고 실행시키기, 테스트 이름 표기 전략 등을 설정 할 수 있다.


###1-12 JUnit 5 확장 모델

JUnit5는 4에 비해 확장 모델이 단순해 졌다. JUnit5의 확장 모델은 Extension 단 하나이다.

일단 SlowTest 대상이 되는 (오래 걸리는 테스트) 테스트들을 찾아 SlowTest의 태깅을 권장하는 기능을 구현해 보도록 하자.

FindSlowTestExtension 이라는 클래스를 만들고 BeforeTestExecutionCallback, AfterTestExecutionCallback 를 구현한다.

```
public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final long THRESHOLD = 1000l;

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        ExtensionContext.Store store = getStore(extensionContext);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        String testMethodName = extensionContext.getRequiredTestMethod().getName();
        ExtensionContext.Store store = getStore(extensionContext);
        long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;
        if (duration > THRESHOLD) {
            System.out.printf("please consider mark method [%s] with @SlowTest.\n", testMethodName);
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getTypeName();
        String testMethodName = context.getRequiredTestMethod().getName();
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }

}
```

확장 모델은 @ExtendWith(FindSlowTestExtension.class) 애노테이션을 테스트케이스의 클래스에 등록 해주면 된다.

>before each  
test1/10  
please consider mark method [repeatTest] with @SlowTest.  
after each  

Thread.sleep을 이용하여 시간을 오래 걸리게 해놓고 테스트를 하면 위와 같은 문구가 실행 되는것을 알 수 있다.

@RegisterExtension 애노테이션을 클래스 안에서 선언하면 위의 확장 클래스를 생성하여 생성자를 호출하게 되고 클래스 안의 값들을 생성자로 주입하는 식의 코딩이 가능하다.

또한 ServiceLoader를 통해 자동으로 생성하게 하는 방법도 있으며 properties 설정 파일을 통해 익스텐션을 자동으로 감지하도록 할 수도 있다.
(어떤 폴더에 넣어야 하는 등의 제약이 있다)

###1-13 JUnit 5 마이그레이션

스프링 부트를 생성하면 JUnit5의 빈티지 엔진이 기본적으로 빠져있다. (빈티지가 있어야 Junit4를 실행 시킬 수 있다)
이를 의존성 추가하면 Junit 3,4의 테스트가 가능하다.

JUnit5 에서는 RunWith를 더 이상 사용 할 필요가 없다.


