Lute tablature converter
By Javier Llaca
------------------------

Converts alphabetic lute tablature to numeric tablature.

The program currently supports tablature for instruments with no
more than 9 strings.



** Input format **

First line of input is the number of strings in the original tablature.
Subsequent lines consist of note and duration parameters in the following format:

(Note)			(Duration)
<String#><Letter>	[<Fraction>] [.]

Example:

3a	1/8 .

(Reads as: third string, fret a (open string), with a duration of an eighth note)

Note and duration parameters are separated by a tab ('\t') character.
The dot ('.') and fraction are separated by a space (' ') character.

Duration parameters are optional. 
A fraction may be entered without a dot.
The dot is invalid without a fraction.

Each note/duration pair occupies an entire line.

Bar lines are denoted by a comma (','), also occupying one line.



** Output format **

Note durations are printed above the tablature.

The labels are:

w	1/1	w(hole)
h	1/2	h(alf)
q	1/4	q(uarter)
e	1/8	e(ighth)
s	1/16	s(ixteenth)
t	1/32	t(hirtysecond)
