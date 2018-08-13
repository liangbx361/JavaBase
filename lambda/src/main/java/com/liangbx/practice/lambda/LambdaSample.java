package com.liangbx.practice.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Company: 17173</p>
 *
 * @author liangbx
 * @version 2018/8/13
 */
public class LambdaSample {

    public static void main(String[] args) {
        List<Person> guiltyPersons = Arrays.asList(
                new Person("Yixing", "Zhao", 25),
                new Person("Yanggui", "Li", 30),
                new Person("Chao", "Ma", 29)
        );

        // 打印出guiltyPersons List里面所有LastName以"Z"开头的人的FirstName

        // 原始的写法 1.8 之前
        NameChecker nameChecker = new NameChecker() {
            @Override
            public boolean check(Person p) {
                return p.getLastName().startsWith("Z");
            }
        };

        Executor executor = new Executor() {
            @Override
            public void execute(Person p) {
                System.out.println(p.getFirstName());
            }
        };
        checkAndExecute1(guiltyPersons, nameChecker, executor);

        // 实现方式一
        // 利用lambda表达式接收函数参数
        checkAndExecute1(guiltyPersons, p -> p.getLastName().startsWith("Z"), p->System.out.println(p.getFirstName()));

        // 实现方式二
        // 利用 Predicate、Consumer 简化对象创建
        checkAndExecute2(guiltyPersons, p -> p.getLastName().startsWith("Z"), p->System.out.println(p.getFirstName()));

        // 实现方式三
        // 利用foreach 简化遍历
        checkAndExecute3(guiltyPersons, p -> p.getLastName().startsWith("Z"), p->System.out.println(p.getFirstName()));

        // 实现方式四
        // 利用steam 特性简化
        guiltyPersons.stream().filter(p -> p.getLastName().startsWith("Z")).forEach(person -> System.out.println(person.getFirstName()));
    }

    public static void checkAndExecute1(List<Person> personList, NameChecker nameChecker, Executor executor) {
        for(Person p : personList) {
            if(nameChecker.check(p)) {
                executor.execute(p);
            }
        }
    }

    public static void checkAndExecute2(List<Person> personList, Predicate<Person> predicate, Consumer<Person> consumer) {
        for(Person p : personList) {
            if(predicate.test(p)) {
                consumer.accept(p);
            }
        }
    }

    public static void checkAndExecute3(List<Person> personList, Predicate<Person> predicate, Consumer<Person> consumer) {
        personList.forEach(p -> {
            if(predicate.test(p)) {
                consumer.accept(p);
            }
        });
    }

    interface NameChecker {
        boolean check(Person p);
    }

    interface Executor {
        void execute(Person p);
    }
}
