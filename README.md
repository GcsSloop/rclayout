# RCLayout(圆角布局)

Android 通用圆角布局，快速实现圆角需求。

之前做项目的时候有圆角相关需求，在网上找了很多方案都不够满意，于是自己做了一个，目前已经使用了一段时间，更新了多个版本，我遇到的问题都进行了修复，并且添加了很多方便的可配置属性，以满足不同需求。

**相关原理解析： [雕虫晓技 · 通用圆角布局全解析(v1.4.7)](http://www.gcssloop.com/gebug/rclayout)**

## 效果预览

<img src="https://ww4.sinaimg.cn/large/006tKfTcly1fk7twywj5oj30u01fewka.jpg" width="300"/>

## 支持的特性

- [x] 包裹任意组件。
- [x] 设置圆角大小。
- [x] 分别对每一个角设置圆角大小。
- [x] 设置描边宽度。
- [x] 设置描边颜色。
- [x] 圆形。
- [x] 支持Padding。
- [x] 圆角抗锯齿。
- [x] 内容可点击区域即为显示区域。
- [x] 是否剪裁自身背景。

## 主要文件

| 名字             | 摘要           |
| ---------------- | -------------- |
| RCRealtiveLayout | 圆角相对布局。 |
| RCImageView      | 圆角图片。     |
| RCHelper         | 圆角辅助工具。 |

### 1. 基本用法

RCRelativeLayout(Round Corner RelativeLayout)，使用圆角布局包裹需要圆角的内容然后添加自定义属性即可

```xml
<com.gcssloop.widget.RCRelativeLayout
    android:padding="20dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:round_corner="40dp">

  	<!--任意View-->
    <ImageView
        android:scaleType="centerCrop"
        android:src="@drawable/test"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    <TextView
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="#aaffffff"
        android:gravity="center"
        android:layout_alignParentBottom="true"
        android:text="圆角测试"/>

</com.gcssloop.widget.RCRelativeLayout>
```

### 2. 配置属性

可以在布局文件中配置的基本属性有五个：

| 属性名称                      | 摘要      | 是否必须设置 | 类型      |
| ------------------------- | ------- | ------ | ------- |
| round_corner              | 总体圆角半径  | 否      | dp      |
| round_corner_top_left     | 左上角圆角半径 | 否      | dp      |
| round_corner_top_right    | 右上角圆角半径 | 否      | dp      |
| round_corner_bottom_left  | 左下角圆角半径 | 否      | dp      |
| round_corner_bottom_right | 右下角圆角半径 | 否      | dp      |
| round_as_circle           | 是否剪裁为圆形 | 否      | boolean |
| stroke_width              | 描边半径    | 否      | dp      |
| stroke_color              | 描边颜色    | 否      | color   |
| clip_background           | 剪裁背景    | 否      | boolean |

### 3. 属性简介

#### 3.1 圆角属性

`round_as_circle` 的权限最高，在默认情况下它的值为false，如果设置这个属性为 true，则会忽略圆角大小的数值，剪裁结果均为圆形。

设置圆角大小的一共有5个属性，一个是全局的圆角大小`round_corner`，其余四个`round_corner_xx_xx`是分别对每一个角进行设置，它们之间存在替代关系。

1. 仅设置全局，所有的角都跟随全局。
2. 仅对某些角设置，则只有设置过的角会有圆角效果。
3. 全局和部分都有设置，则有具体设置的角跟随具体设置的数值，没有具体设置的角跟随全局设置。

#### 3.2 描边属性

描边宽度`stroke_width`默认情况下数值为 0，即不存在描边效果。  
描边高度`stroke_color`默认情况下为白色，允许自定义颜色。

#### 3.3 背景剪裁

RCLayout 默认对自身背景剪裁，但是可以通过设置 clip_background 为 false 让RCLayout 不剪裁自身的背景。

### 4.添加方法

#### 4.1 添加仓库

在项目的 `build.gradle` 文件中配置仓库地址。

```groovy
allprojects {
    repositories {
        jcenter()
        // 私有仓库地址
       maven { url "http://lib.gcssloop.com:8081/repository/gcssloop-central/" }
    }
}
```

#### 4.2 添加项目依赖

在需要添加依赖的 Module 下添加以下信息，使用方式和普通的远程仓库一样。

```groovy
compile 'com.gcssloop.widget:rclayout:1.5.2@aar'
```


## 作者简介

#### 作者微博: [@GcsSloop](http://weibo.com/GcsSloop)

#### 个人网站: http://www.gcssloop.com

<a href="http://www.gcssloop.com/info/about/" target="_blank"> <img src="http://ww4.sinaimg.cn/large/005Xtdi2gw1f1qn89ihu3j315o0dwwjc.jpg" width="300"/> </a>

## 更新日志

#### v1.5.2

默认不剪裁背景，即 clip-background 默认为 false。  
修复剪裁不完全的情况。

#### v1.5.1

完善布局属性提示。

#### v1.5.0

添加 RCImageView (属性和 RCRelativeLayout 相同)。  
抽取 RCHelper。

#### v1.4.7

增加 `clip_background` 属性，控制对 RCLayout 的背景剪裁默认为 true(剪裁)。

#### v1.4.6

对 RCLayout 背景剪裁 (背景剪裁暂不支持抗锯齿)。

#### v1.4.5

描边支持半透明。

#### v1.4.4

优化性能(回收 TypedArray)。

#### v1.4.3

优化性能(移除在dispathcDraw中创建的对象)。

#### v1.4.2

尝试修复屏幕像素密度过低时出现边框问题。

#### v1.4.1

修复圆形状态下Padding值问题。

#### v1.4.0

限定点击区域为显示区域。

#### v1.3.0

支持描边。  
添加描边宽度，描边颜色两个属性。

#### v1.2.0

支持圆形。

#### v1.1.0

更新圆角实现方式。  
添加抗锯齿。

#### v1.0.0

允许对每一个角分别设置半径。  
支持padding。

#### v1.0.0-alpha

测试圆角方案是否可行。  
允许统一设置圆角半径。


## 版权信息

```
Copyright (c) 2017 GcsSloop

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```