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

## TDD (Test Driven Development)
- プロダクションコードよりテストコードを先に作成することによってテストが具現（実装）過程を主導させる方法論
- RED(失敗するテスト) -> GREEN(一早い時間にテスト通貨の為の最初限のコード) -> REFACTOR(実装コード改善、テスト通過維持)
- TDDの核心的なことは「フィードバック」
- テストは実装を検証するための補助手段ではなく実装と相互作用しながら発展するものである
- 観点の変化！
```text
クライアントの観点からフィードバックを与えるTest Driven!
```
### 先テスト作成、後機能実装
- 複雑度が低い、テスト可能なコードで実装できるようになる
  - 柔軟で維持補修しやすい
- よく見つけられないエッジケースを作成することができる
- **実装に対する一早いフィードバックが貰える**
- 大胆なリファクタリングができる
### 先機能具現、後テスト作成の問題点
  - テストを書かない可能性が高い
  - 特定のテストケースのみを検証する可能性が高い（ハッピーケースのみ作成するとか）
  - 誤った実装を見つける可能性が低くなる