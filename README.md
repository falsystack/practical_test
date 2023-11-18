# 実用的なテストの書き方（Practical Test）
- 簡単なカフェキオスクアプリケーションを作りながら学

## 単体テスト（Unit Test）
- 小さいコード単体を独立的に検証するテスト
  - クラス又はメソッド
  - 外部に依存しない一番小さいテストの単位
- 検証速度が早く、安定的
- JavaではJuint5を使う
  - 単体テストの為のテストフレームワーク
  - XUnit - Kent Beck
  - https://junit.org/junit5/
- AssertJ
  - テストこーど作成を円滑に助けてくれるライブラリ
  - 豊富なAPI

## テストケースの細分化
- ハッピーケース
- 例外ケース
- どちらも境界(範囲、区間、日付等々)テストが重要

## テストし辛い領域を分離する
```java
// テストしづらいコード
public Order createOrder(){
  var currentDateTime = LocalDateTime.now();
  var currentTime=currentDateTime.toLocalTime();
}

// テストし辛い領域を分離する。
public Order createOrder(LocalDateTime currentDateTime){
  var currentTime=currentDateTime.toLocalTime();
}
```
- 外部に分離することでテスト可能なコードが多くなる。
- テストをしようとする領域とテストがし辛い領域を区分することが大事だ。
### テストがしづらい領域の例
- 観測（観察、照会）する度に他の値に依存するコード
  - 日付、時間、ランダム数、グローバル変数、クライアントのインプット値等々
- 外部領域に影響を与えるコード
  - std.out, メッセージ発送、データベースに記録等々