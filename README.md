# SimpleToDo

# APIインターフェース仕様

* APIは4つ
** 全件取得、新規追加、更新、削除
* エンドポイント：https://5f9ymkv3c9.execute-api.ap-northeast-1.amazonaws.com

## GET

* 全件のタスクを取得する
* https://5f9ymkv3c9.execute-api.ap-northeast-1.amazonaws.com/api
* 入力パラメータはなし
* 出力形式
<pre>
{
  "Items": [
    {
      "id": 156,
      "priority": 3,
      "title": "aaaa"
    },
    {
      "id": 155,
      "priority": 5,
      "title": "bbbb"
    },
  ],
  "Count": 2,
  "ScannedCount": 2
}
</pre>

## POST

* 新規登録
* https://5f9ymkv3c9.execute-api.ap-northeast-1.amazonaws.com/api?priority=3&title=testmessage
* 入力パラメータ
** priority：優先度。数値型。現状は1、3、5
** title：タスク名。文字列型。
* 出力は未使用

## PUT

* 更新
* https://5f9ymkv3c9.execute-api.ap-northeast-1.amazonaws.com/api?priority=3&title=testmessage&id=1
* 入力パラメータ
** id：プライマリキー。数値型。
** priority：優先度。数値型。現状は1、3、5
** title：タスク名。文字列型。
* 出力は未使用

## DELETE

* 削除
* https://5f9ymkv3c9.execute-api.ap-northeast-1.amazonaws.com/api?id=1
* 入力パラメータ
** id：プライマリキー。数値型。
* 出力は未使用
