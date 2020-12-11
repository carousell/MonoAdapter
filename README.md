# MonoAdapter

General Adapter for single ViewType cases.

## Install

With setup of Jitpack first, than add dependency in your build.gradle
```groovy
implementation 'com.github.carousell:MonoAdapter:1.0.0'
```

## Usage & Example
1. With ViewBinding: You can easily declare the ViewBinding type and the data type, and then provide how to bind data with the binding object like below.

```kotlin
val adapter = MonoAdapter.create<AdapterMyDataBinding, MyData> {
    textView.text = it.text1
    button.setOnClickListener {
        textView.text = it.text2
    }
}
```
2. Without ViewBinding: ViewBinding is preferred but not necessary, you can pass the layout directly and then bind the View and data in the lambda like below example.

```kotlin
val adapter = MonoAdapter.create<MyData>(R.layout.adapter_sample) { view, data ->
    val textView = view.findViewById<TextView>(R.id.text)
    textView.text = data.text1
    view.findViewById(R.id.button).setOnClickListener {
        textView.text = data.text2
    }
}
```
Go to ./app module for more information.


## Contributing

Thank you for being interested in contributing to this project. Check out the [CONTRIBUTING](https://github.com/carousell/MonoAdapter/blob/master/CONTRIBUTING.md) document for more info.

## About

<a href="https://github.com/carousell/" target="_blank"><img src="https://avatars2.githubusercontent.com/u/3833591" width="100px" alt="Carousell" align="right"/></a>

**MonoAdapter** is created and maintained by [Carousell](https://carousell.com/). Help us improve this project! We'd love the [feedback](https://github.com/carousell/MonoAdapter/issues) from you.

We're hiring! Find out more at <http://careers.carousell.com/>

## License

**MonoAdapter** is released under Apache License 2.0.
See [LICENSE](https://github.com/carousell/MonoAdapter/blob/master/LICENSE) for more details.
