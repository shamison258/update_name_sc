# update_name_sc

update_name の scala 実装

## update_nameとは

Twitter上でリプライ等のツイートで, Twitterのスクリーンネームを変更するアプリ

## 必要

- scala
- sbt
- twitter api key

## ビルド 及び 設定

1. `src/main/resources/twitter4j.properties`を作成し api keyを設定 
   - [twitter4jドキュメント](http://twitter4j.org/ja/configuration.html)
   - ↑の「1. twitter4j.properties から」の項目参照
2. 以下のコマンドによりビルド 
   - `./target/scala-2.11/update_name_sc-assembly-1.0.jar`が作られる。
```
$ sbt assembly
```
  

## 実行例

- サーバ側の設定

```sh
$ java -jar update_name_sc-assembly-1.0.jar
```

