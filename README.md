# JDK 8 &#955; and why I should care

This document and code will try to explain the basic concepts behind Higher Order Functions, Lambda's and the surrounding features introduced in Java 8.
Java came quite late to the party, and many other languages support &#955; and _HOF_ for a long time, for example: Python, Haskell, F#, Scala and... JavaScript!
&#955; and _HOF_ are closely related to functional languages and functional programming, but this doesn't mean they can't be used in an imperative world. As we will see, Java can take a big advantage by using them.


## What is a &#955;?
### Long story short
A lambda is an anonymous function and if you ever developed in JavaScript, you used anonymous functions in the past. Here you can see an example of a&#955;:
``` lang=java
 (a + b) -> a + b
```
### Longer story
Anonymous functions originate in the work of [Alonzo Church](https://en.wikipedia.org/wiki/Alonzo_Church) in his invention of the [lambda calculus](https://en.wikipedia.org/wiki/Lambda_calculus)in 1936 (prior to electronic computers), in which all functions are anonymous.
The syntax of the &#955; is the following:
* A comma-separated list of formal parameters enclosed in parentheses.
  Note: You can omit the data type of the parameters in a lambda expression. In addition, you can omit the parentheses if there is only one parameter.
* The arrow token: ->
* A body, which consists of a single expression or a statement block.
  If you specify a single expression, then the Java runtime evaluates the expression and then returns its value. Alternatively, you can use a return statement.

You can consider &#955; as some kind of syntactic sugar for Functional Interfaces; the abstract method in Functional interfaces will be implemented by the body of the lambda you defined. It also brings type inference to Java, meaning that type declaration for the arguments can be skipped most of the times.

## Functional Interfaces
Functional Interfaces are the Java solution to bring lambda's to Java and make functions somehow First Class Citizens. As you can guess from its name, Functional Interfaces are interfaces with some characteristics:
* They are annotated with `@FunctionalInterface`
* They have only **one** abstract method
* They might have many other default implementation methods


As stated before, a &#955; will only compile if it can find an interface annotated with `@FunctionalInterface` which abstract method signature matches the one of the &#955;

We can group Functional Interfaces in several groups:
* New Interfaces created:
  * Consumer: The `Consumer<T>` can be used for &#955; that take one argument of type `T` and return void. They are performing work via Side Effects.
  * Supplier: The `Supplier<T>` can be used for &#955; that take no arguments and return an object of type `T`.
  * Function: The `Function<T,R>` can be used for &#955; that take one argument of type `T` and return an object of type `R`.
  * Predicate: The `Predicate<T>` can be used for &#955; that take one argument of type `T` and return a boolean.
  * Each of these types have different specializations:
    * int, long and double specializations to overcome the extra cost of boxing and unboxing. You can skip these if your application is not that performance critical.
    * Generic binary operation of them (expect Supplier):
      * `BiConsumer<T,U>`, `BiFunction<T,U,R>` and `BiPredicate<T,U>` which differ from the previous only on the number of arguments.
    * Specific specializations when the parametrized types coincide:
      * `UnaryOperator<T>` which extends `Function<T,T>`.
      * `BinaryOperator<T>` which extends `BiFunction<T,T,T>`.
      * The plethora of primitive specializations for them.
* Already existing Interfaces:
  * Runnable: Starting threads which body are &#955; is now possible.
  * Callable: Starting threads which body are &#955; which return a future value is now possible.
  * Comparator: Whenever a comparable is expected, a &#955; can be provided, this means all sort methods in collections or constructors in sorted collections.

Of course, as a developer you can define your own methods that take a Functional Interface as an attribute and the callers of these methods can make use of &#955;.

### Composability
Many of the Functional Interfaces provided in the Standard Library provide methods to compose &#955;. For example, Predicates have the typical boolean logic combinators (and, or and negate); others like Function offer the `andThen` and the `compose` combinators. This functionality is provided via default method implementations within the Functional Interfaces.

## Streams
This is a new class introduced in Java 8. Its main purpose is to perform operations with its elements through _HOF_. The main difference between Streams and Collections is that the later are concerned about efficient management and accessibility of its elements. Meanwhile, Streams don't offer direct access to its elements and force the developer to manipulate its elements via declarative instructions. Once a Stream is closed, no further operations can be executed on that same instance.
### Laziness
Streams are lazy, this means that operations made in it are only evaluated on demand, this is when a terminal operation is called. This enables the possibility to deal with potential infinite elements. These are all the methods that will cause the evaluation of the stream:
* `sorted`
* `forEach` and `forEachOrdered`
* `toArray` and the other overloaded method
* `reduce` and the other 2 overloaded methods
* `collect` and the other overloaded method
* `min`, `max` and `count`
* `anyMatch`, `allMatch` and `noneMatch`
* `findFirst` and `findAny`

### Most Useful functions in Stream
All functions defined under Stream have a meaning and a purpose and they can come handy under specific circumstances. Some of the most common ones will be described below:
* `map`: This function is used to apply some transformation to each element in the stream, it takes a `Function<? super T,? extends R>`. Note that the result might be another type different than the original one that formed the Stream.
* `reduce`: This function is used to _reduce_ the stream to a different type. Typically, it applies some aggregation function to each element in the collection. Note that reduce starts from the _left_ and continues towards the _right_.
* `filter`: This function takes a Predicate and is used to filter elements which satisfies the provided predicate.
* `limit`: This function doesn't take any Functional Interface and it's useful when we are interested in only a subset of all the elements which satisfy all the other intermediate.
* `findFirst`: This function doesn't take any Functional Interface, and as `limit` is useful when we are only interested in the first element which satisfies all the other intermediate.

### Parallel Streams
One of the new functionality of Streams is that they can be easily converted to a parallel stream, meaning that the functions called on streams are spawned to different Threads. **Use parallel streams with caution!**. Performance is not improved just by converting a stream to a parallel one, other aspects come into play as well. Furthermore, spawning new Threads under a JEE context is considered a bad practice and is discouraged.
Never trust given benchmarks, to be sure if it's better to use parallel streams instead of its sequential counterpart, perform benchmarks first and not only on a Developer Machine. Commonly, streams need to be big and the task to perform with each element must be CPU intensive and satisfy associativity and commutativity laws.
To convert a stream to a parallel one just call `parallel()` on streams, or directly generate a parallel stream from a Collection calling `parallelStream()`.

## Some Best Practices
The following are some initial best practices and it should be seen as a living document.
* Prefer readability over fanciness.
* Use type inference in &#955; parameters, unless the compiler can't infer it, obviously.
* If &#955; have only 1 parameter parenthesis can be left out.
* Do not write long body &#955;. Think again if the resulting &#955; is still readable.
* If a method you write has a Functional Interface as parameter, place it last in the parameter list.
* Streams are linear, they don't play well with Sets, Maps and multi-dimensional Arrays.
* A method reference is not always more readable than an expanded &#955;.
* Do not use parallel streams in a JEE context.
* Use parallel streams in a non JEE context only if you proved that it will be faster. Even then, think twice before using them.
* It is OK to extract &#955; into local variables and use them in _HOF_.
* Do not use &#955; as a parameters in EJB calls.

## Optional Type
This is one of the solutions to the 'Billion Dollar Mistake' a.k.a. NullPointerException. This class is typically used to express that a method returns an object that might or might not be null, so instead of letting the caller of this function deal with the complexity of checking whether is null or not, a new type is created to deal with it. As in 'Stream' there are some _HOF_ methods like `map`, `flatMap` and `filter`. One useful method is `orElse` which is an elegant way of retrieving the enclosed value, if any, providing a default in case it doesn't exist. Some do's and don'ts:
* Never create an `Optional` with a null!. **Never, ever.**
* Prefer `orElse` and 'map' over the plain `get` even if combined with `isPresent`.
