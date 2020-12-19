# MonoAdapter

[![CircleCI](https://circleci.com/gh/carousell/MonoAdapter.svg?style=shield)](https://circleci.com/gh/carousell/MonoAdapter)
[![jitpack](https://jitpack.io/v/carousell/MonoAdapter.svg)](https://jitpack.io/#carousell/MonoAdapter)


General Adapter for single ViewType cases.

## Install

With setup of Jitpack first, than add dependency in your build.gradle
```groovy
implementation 'com.github.carousell:MonoAdapter:2.0.0'
```

## Usage & Example
1. With ViewBinding: You can easily declare the ViewBinding type and the data type, and then provide how to bind data with the binding object like below.

```kotlin
val adapter = MonoAdapter.create<AdapterMyDataBinding, MyData>(AdapterMyDataBinding::inflate) {
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

p.s. You can also pass the `DiffUtil.ItemCallback` into each adapter create function to identify how to do the diff check for your data. MonoAdapter will otherwise compare the data as a whole if you didn't specified.

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
