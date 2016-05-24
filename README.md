# Enterlab Mather

A small fun math tester for everybody, made for my kid who wants to improve in multiplication.

## Usage

`lein repl`

If not in namespace `enterlab.mather.core`, make sure to be:

```
(require 'enterlab.mater.core)
(in-ns 'enterlab.mather.core)
```

To start quizzing, you need to tell Mather who you are (just your first name), the smallest number you want to calculate with, the largest number, how many questions you want, and a language (currently :en for English and :da for Danish is supported):

`(quiz "luposlip" 4 13 10 :en)`

In this example I save (and read) statistics for the user called `luposlip`. The minimum number to multiply is `4`, maximum number is `13` and I have to do `10` questions.

The results are saved in a text file with the extension of `.mather`. In example `luposlip-results.mather`.

Personal performance is measured as seconds pr. correct answer, and grouped with other test runs with same minimum and maximum multiplier input.

## TODO

- other types of calculation (division, addition, subtraction)
- run directly from command line
- optionally look at number of questions for statistical comparation
- all time best of all users
- other statistics from `.mather` file

Pull Requests are warmly welcomed!

## More languages?

If anyone is interested, I'd happily accept translations into other languages. Pull Requests are welcome!

## Example Output 

```
enterlab.mather.core=> (quiz "luposlip" 7 13 2 :en)

===== Multiply numbers between 7 and 13 (2 repetitions) =====
  - Tried 7 times before
  - Your record of 1.8 seconds pr. correct answer is from 05-25-2016
===== Press ENTER when ready!! =====

7 times 11? 77
Correct!
8 times 12? 96
Correct!
===== RESULT =====
 You had 2 correct and 0 wrong answers.
 It took you approximately 2 seconds to answer.
 That's 0.99 seconds pr. correct answer.
 *** CONGRATULATIONS, you've beat your own personal best with 0.84 seconds pr. correct answer!! :-) ***
 ```

## Recommended use
 
In real life it makes better sense with more questions. For training multiplication with kids, 10-20 questions is probably suitable for most.

And then after a (very) short break, repeat again. 10-20 repetitions of 10-20 questions every day in e.g. a month makes a huge difference and often helps a lot. Kids (and the rest of us) loves the "gamification" and competing against ourselves.

## Added Value

Trainees can also become more sufficient in using the number keys on the keyboard, all while becoming better at math! :)

## License

Copyright © 2016 Enterlab ApS

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
