# DGS

## module说明

- a-start：简单的使用
- b-codegen：使用codegen，Entity的代码自动生成，多module
- c-scalar：支持自定义类型
- d-http：支持Query，Mutation，Subscription，参数校验
- e-file：支持文件上传下载
- f-auth：支持认证和授权
- g-error：支持错误类型
- y-bff和z-domain：支持Client和Server

## Intellij Idea Plugin的安装

- GraphQL
- DGS

## 问题

1. 减少代码编写量是否支持？例如只编写`*.graphqls`，Request和Response自动生成

> 查看`b-codegen`

2. 自定义类型如何支持？例如Long，BigDecimal，UUID

> 查看`c-scalar`，支持自定义类型，也搭配了`codegen`进行使用

2. 是否支持HTTP的所有方法？参数校验如何支持？

> d

3. 文件上传MultiPartFile如何支持？

> e

5. 认证和授权如何支持？

> f

6. 错误类型如何支持？

> g

7. GraphQL作为Client调用提供GraphQL的Server如何支持？

> y,z

## a-start

- 启动，访问http://localhost:10001/graphiql
- 输入

```
{
    shows {
        title
        releaseYear
    }
}
------
{
  shows(titleFilter: "Ozark") {
    title
    releaseYear
  }
}
------
{
  showsWithDgsData {
    id
    title
    releaseYear
    actors {
      name
    }
  }
}
```

- 输出

```
{
  "data": {
    "shows": [
      {
        "title": "Stranger Things",
        "releaseYear": 2016
      },
      {
        "title": "Ozark",
        "releaseYear": 2017
      },
      {
        "title": "The Crown",
        "releaseYear": 2016
      },
      {
        "title": "Dead to Me",
        "releaseYear": 2019
      },
      {
        "title": "Orange is the New Black",
        "releaseYear": 2013
      }
    ]
  }
}
------
{
  "data": {
    "shows": [
      {
        "title": "Ozark",
        "releaseYear": 2017
      }
    ]
  }
}
------
{
  "data": {
    "showsWithDgsData": [
      {
        "id": "1",
        "title": "Stranger Things",
        "releaseYear": 2016,
        "actors": [
          {
            "name": "zhangsan"
          },
          {
            "name": "lisi"
          }
        ]
      },
      {
        "id": "2",
        "title": "Ozark",
        "releaseYear": 2017,
        "actors": null
      },
      {
        "id": "3",
        "title": "The Crown",
        "releaseYear": 2016,
        "actors": null
      },
      {
        "id": "4",
        "title": "Dead to Me",
        "releaseYear": 2019,
        "actors": null
      },
      {
        "id": "5",
        "title": "Orange is the New Black",
        "releaseYear": 2013,
        "actors": null
      }
    ]
  }
}
```

## b-codegen

- root的build.gradle添加

```
plugins {
    id "com.netflix.dgs.codegen" version "5.1.17" apply false
}
```

- module的build.gradle添加

```
plugins {
    id "com.netflix.dgs.codegen"
}
```

- module的build.gradle添加并运行

```
generateJava{
    schemaPaths = ["${projectDir}/src/main/resources/schema"] // List of directories containing schema files
    packageName = 'com.codegen.graphqldgs' // The package name to use to generate sources
    generateClient = true // Enable generating the type safe query API
}
```

- 查看build文件夹下生成的产物
- 输入，访问http://127.0.0.1:10002/graphiql

```
{
  shows {
    id
    title
    releaseYear
  }
}
------
{
  shows(titleFilter: "Ozark") {
    id
    title
    releaseYear
  }
}
```

- 输出

```
{
  "data": {
    "shows": [
      {
        "id": 1,
        "title": "Stranger Things",
        "releaseYear": 2016
      },
      {
        "id": 2,
        "title": "Ozark",
        "releaseYear": 2017
      },
      {
        "id": 3,
        "title": "The Crown",
        "releaseYear": 2016
      },
      {
        "id": 4,
        "title": "Dead to Me",
        "releaseYear": 2019
      },
      {
        "id": 5,
        "title": "Orange is the New Black",
        "releaseYear": 2013
      }
    ]
  }
}
------
{
  "data": {
    "shows": [
      {
        "id": 2,
        "title": "Ozark",
        "releaseYear": 2017
      }
    ]
  }
}
```

## c-scalar

- 输入，访问http://127.0.0.1:10003/graphiql

```
{
  shows {
    id
    dateTime
    price
    bigDecimal
    uuid
  }
}
```

- 输出

```
{
  "data": {
    "shows": [
      {
        "id": 1,
        "dateTime": "2022-04-03T22:30:23.149762+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "93ed1632-8da2-4adc-858d-1a2c003e1f79"
      },
      {
        "id": 2,
        "dateTime": "2022-04-03T22:30:23.149942+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "b966497e-6785-4220-bfcc-ef9a296a4072"
      },
      {
        "id": 3,
        "dateTime": "2022-04-03T22:30:23.150033+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "1e5c4216-7076-456f-90a8-2972da743a63"
      },
      {
        "id": 4,
        "dateTime": "2022-04-03T22:30:23.150059+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "20b2339a-e7fa-433d-add4-1dfed7516c23"
      },
      {
        "id": 5,
        "dateTime": "2022-04-03T22:30:23.150138+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "ab90a2c2-797e-41c4-9bb8-2a429cbf0272"
      }
    ]
  }
}
```
