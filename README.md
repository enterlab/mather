# Enterlab Mather

A small fun math tester for everybody, made for my kid who wants to improve in basic addition.

## Usage

`lein repl`

If not in namespace `enterlab.mather.core`, make sure to be:

```
(require 'enterlab.mater.core)
(in-ns 'enterlab.mather.core)
```

To start quizzing, you need to tell Mather who you are (just your first name), the smallest number you want to calculate with, the largest number, and how many repetitions you want:

`(quiz "luposlip" 4 13 10)`

In this example I save (and read) statistics for the user called `luposlip`. The minimum number to add is `4`, maximum number is `13` and I have to do `10` repetitions.

The results are saved in a text file with the extension of `.mather`. In example `luposlip-results.mather`.

## DISCLAIMER

Right now the output is in **DANISH** only! If anyone is interested, I'd happily translate it to English. Other languages? Pull Requests are welcome!

## License

Copyright © 2016 Enterlab ApS

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
