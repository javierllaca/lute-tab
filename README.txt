Lute tablature converter
By Javier Llaca
------------------------

Converts alphabetic renaissance lute tablature to numeric tablature.



** Input format **

First line of input is the number of strings in the original tablature.
Subsequent lines consist of note and duration parameters in the following format:

(Note)			(Duration)
StringLetter		[<Fraction>][.]

Example:

3a	1/8.
(third string, fret a (open string), dotted eighth note duration)

Note and duration parameters are separated by a tab ('\t') character.

Note parameters are required.
Duration parameters are optional.
	A fraction may be entered without a dot.
	A dot may not be entered without a fraction.

Bar lines are denoted by a comma (',')

Refer to sample input file for more details.



** Output format **

Note durations are printed above the tablature.

The labels are:

w	1/1	w(hole)
h	1/2	h(alf)
q	1/4	q(uarter)
e	1/8	e(ighth)
s	1/16	s(ixteenth)
t	1/32	t(hirtysecond)
.	dot	increases note duration by half its value
