# nanpa-unpa
ni li toki nasa pi ilo nanpa. sina sitelen  e ni kepeken nanpa pi toki pona.  

[toki pona](https://tokipona.org/ "Toki Pona(official site)")という人工言語の、"数"の表し方を題材にした難解プログラミング言語です。  
自作Esolang文化布教のために作ったもので、初心者が勢いだけで実装できる言語仕様を意識しました。  
もちろん[Brainf*ck](https://ja.wikipedia.org/wiki/Brainfuck "Brainf*ck - Wikipedia")の方言(実質的なトランスパイル言語)です。

## Requirements

### Runtime
jar版を利用して実行する場合、JRE SE 21以降が必要です。

### Development
ソースコードをコンパイルする場合は、JDK SE 14以降が必要です。  
(switch式周りの構文を古いものに書き換えれば13以前でも動くかもしれません。要検証)

## Installation
[Releases](https://github.com/Bokume2/nanpa-unpa/releases)からjar版をダウンロードするか、  
src/java内のソースコードを適宜コンパイルして下さい。

## Usage
以下、カレントディレクトリにjar版(nanpa-unpa.jar)が置かれている前提で操作例を挙げます。  
ソースコードからコンパイルした方などはよしなに読み替えて下さい。

### Run
nanpa unpaのソースコード oke.nanpaunpa を実行する例:  
```
$ java -cp nanpa-unpa.jar com.nanpaunpa.NanpaUnpa oke.nanpaunpa
```

### Transpile
Brainf\*ckのソースコード fuga.bf をnanpa unpaに変換して pijo.nanpaunpa に書き込む例:
```
$ java -cp nanpa-unpa.jar com.nanpaunpa.NUTranspiler fuga.bf pijo.nanpaunpa
```
なお、**nanpa unpaからBrainf\*ckへの変換には対応しておりません**。~~実装するのが面倒なので~~

## Syntax
Brainf\*ckの構文を基本とし、各命令をtoki ponaの数によって番号で記述します。  
数を表すキーワード列は、1命令ごとに末尾の`.`で区切ります(下記[Numbers](https://github.com/Bokume2/nanpa-unpa#numbers)参照)。

### Keywords
toki ponaには、明確に数を表す単語が以下の3つしか存在しません。
- `wan` -> 1  
- `tu` -> 2  
- `luka` -> 5

### Numbers
上記のキーワードを単純に並べ、各々が表す数の合計を数えます。  
- `tu wan.` -> 3 -> `<` in BF
  - `wan tu.`や`wan wan wan.`も可(以下別表記は省略)
- `luka wan.` -> 6 -> `.` in BF
- `luka tu tu.` -> 9 -> \<Syntax Error\>

空白類は必須ではありませんが、あった方が可読性が高く、何よりtoki ponaらしいでしょう。

### Instructions
命令番号、Brainf\*ckでの記述、実行される操作の概要の対応表は以下の通りです。
| Number | BF Char | Instruction |
|:------:|:-------:|:------------|
| 1 | + | increment |
| 2 | - | decrement |
| 3 | < | move left |
| 4 | > | move right |
| 5 | , | input |
| 6 | . | output |
| 7 | \[ | loop start |
| 8 | \] | loop end |

## Examples
samplesディレクトリ内の toki.nanpaunpa は、`Hello, world!`という英文を(僕なりに)toki ponaへ翻訳した以下の文字列を出力します。
```
toki, ali o!
```

## Contribution
「初心者が勢いだけで作れる」というコンセプトのために、僕の拙いコーディング技術をそのまま残している部分が多々あります。**それは仕様です**。  
基本的に実装難易度の上がるような修正・追加は行わない予定ですので、**バグ修正などを除くissueやPRにはお応えできない可能性が高いです**。  
ご厚意はありがたく受け取らせて頂きますが、どうかご了承下さい。
