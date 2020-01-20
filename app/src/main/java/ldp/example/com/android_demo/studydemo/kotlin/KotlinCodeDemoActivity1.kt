package ldp.example.com.android_demo.studydemo.kotlin


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_kotlin_code_demo1.*
import ldp.example.com.android_demo.R
import java.util.*

/**
 *  Kotlin 基础语法
 *
 *  1、
 *
 */
class KotlinCodeDemoActivity1 : AppCompatActivity() {

    val s = "Example" // A Immutable String 不可变变量
    var v = "Example" // A Mutable String 可变变量
    val args: Array<Int> = arrayOf(1, 2, 3)
    val arrayOfNulls = arrayOfNulls<Int>(10) //空数组
    val initArray = Array(5, { i -> (i * i).toString() }) //构造函数init

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_code_demo1)
        initView()
    }

    /**
     * 通过对象表达式实现一个匿名内部类的对象用于方法的参数中
     */
    private fun initView() {
        //Java 匿名对象 kotlin 写法
        btn_1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showToast("匿名类对象实现监听")
            }
        })

        // lambda 表达式 简化
        btn_1.setOnClickListener { showToast("匿名类对象实现监听") }

        btn_2.setOnClickListener(OnClickListener())
    }

    inner class OnClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            showToast("内部类对象实现监听")
        }

    }


    /**
     * 用object 修饰的类为静态类，里面的方法和变量都为静态的。
     */
    object DemoManager {
        private val TAG = "DemoManager"

        fun a() {
            Log.e(TAG, "此时 object 表示 声明静态内部类")
        }


    }

    class DemoManager2 {

        constructor(){
            //构造函数
            MyObject.a()
        }

        /**
         * 类内部的对象声明，没有被inner 修饰的内部类都是静态的
         */
        object MyObject {
            private val TAG = "DemoManager"
            fun a() {
                Log.e(TAG, "此时 object 表示 直接声明类")
            }
        }
    }

    /**
     * companion object 修饰为伴生对象,伴生对象在类中只能存在一个，
     * 类似于java中的 static 静态方法 Java 中使用类访问静态成员，静态方法。
     */

    companion object {

        private val MY_TAG = "DemoManager"

        fun test1() {
            Log.e(MY_TAG, "此时 companion objec t表示 伴生对象")
        }
    }


    /**
     * 在 Kotlin 中,没有Java中三元运算符（条件 ? 然后 : 否则）,
     * 因为if - else 是一个表达式，即它会返回一个值
     */
    fun ifElse() {
        val num1 = 1
        val num2 = 2
        val max = if (num1 > num2) num1 else num2
        println(max)
        println(if (num1 < num2) "if - else 表达式" else num2)


        //if的分支可以是代码块，最后的表达式作为该块的值
        println(
                if (num1 < num2) {
                    println("num1 < num2")
                    "if - else 表达式"
                } else num2
        )
    }

    /**
     * 在 Kotlin 中,when 取代了Java中 switch
     */
    fun whenTest() {
        when {
        } //最简单的形式

        val randomNum = Random().nextInt(5)
        when (randomNum) {
            0, 1 -> println("randomNum == 0 or randomNum == 1") //多个分支条件放在一起，用逗号分隔
            2 -> println("randomNum == 2")
            else -> println("otherwise")
        }

        //如果不提供参数，所有的分支条件都是简单的布尔表达式，而当一个分支的条件为真时则执行该分支
        when {
            randomNum == 0 -> {
                var a = 2; println("is 0")
                var b = 3
                println("is 0") //换行
            }
        }
        //其他分支都不满足条件将会求值 else 分支
        when {
            randomNum == 0 -> println("randomNum is 0")
            randomNum == 1 -> println("randomNum is 1")
            else -> println("otherwise")
        }

        val an = when (1) {
            1 -> 1
            else -> "never arrive"
        }
        println(an)
        when (randomNum == 3) {
            true -> println("is 3")
            false -> println(randomNum)
        }
    }

    /**
     *  for循环
     */
    fun forTest() {

//        for (item in collection) print(item)
//        for (index in collection.indices) print(collection[index])

        val array = arrayOf(1, 2, 3)
        //for
        for (index in array.indices) print(array[index]);println() //索引遍历一个数组或者一个 list
        for (item in array) print(item);println()
        //库函数 forEachIndexed
        array.forEachIndexed { index, item -> print("[$index] = $item \t") }
        println()
        //库函数 withIndex
        for ((index, value) in array.withIndex()) {
            println("the element at $index is $value")
        }
        val a =1
        val b = 10
        if (a in 2..10){
            println(true)
        }
    }


    //Kotlin 十分简便, 可以在主构造函数内声明属性（可变的（var）或只读的（val））以及初始化属性默认值（次构造函数是不允许的）, 且为该类成员属性, 主构造函数内不能包含除了声明属性任何的代码。提供了 init 关键字作为前缀的初始化块（initializer blocks）。
    //
    open class Person /*private*/ constructor(firstName: String) {
        class A //empty class 下面接着是次构造函数 ，Error: Expecting member declaration, 期待成员声明

        val money = 1000_000

        init {
            println("init block: firstName= $firstName")
            println("init block: money= $money")
        }

        //声明在类体内以 constructor 关键字的函数。若该类有主构造函数,次构造函数都需要用 this 关键字直接或间接委托给主构造函数

        //次构造函数
        constructor(firstName: String, age: Int) : this(firstName) {
            println("secondary constructor: firstName= $firstName")
            println("secondary constructor: age= $age")
            println("init block: money= $money")
        }

        constructor (firstName: String, age: Int, money: Int) : this(firstName, age) {
            println("secondary constructor: firstName= $firstName")
            println("secondary constructor: age= $age")
            println("init block: money= $money")
        }

    }

    // Java 的超类是 Object , 而 Kotlin 的是 Any。

    open class Human {
        constructor(name: String) {

        }

        constructor(name: String, age: Int) {

        }
    }

    //继承
    class Woman : Human {
        constructor(name: String) : super(name)
        constructor(name: String, age: Int) : super(name, age)
    }

    //允许通过主构造函数覆盖次构造函数
    class Man(name: String) : Human(name)

    fun showToast(s: String) {
        Toast.makeText(this@KotlinCodeDemoActivity1, s, Toast.LENGTH_SHORT).show()
    }

}
