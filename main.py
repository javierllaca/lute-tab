import re, sys

def barline(n):
	barline = " \n"
	for i in range(0, n):
		barline += "|\n"
	return barline

def parseDuration(s):
	map = {1:"w", 2:"h", 4:"q", 8:"e", 16:"s", 32:"t"}
	number = int(s[s.index("/") + 1:].replace(".", ""))
	return " " + map[number] + ("." if s[len(s) - 1] is '.' else " ")

def matchNumber(s, i):
	matches = re.finditer("[0-9][a-z]", s)
	for match in matches:
		if str(i) == match.group(0)[0]:
			return ord(match.group(0)[1]) - ord('a')
	return -1

def parseNotes(s):
	result = ""
	for i in range(0, numberStrings):
		match = matchNumber(s, i + 1)
	    	if match != -1:
			result += "-%d-\n" % match
		else:
			result += "---\n"
	return result

def tabColumn(s):
	params = s.split("\t")
	return (parseDuration(params[1]) if len(params) > 1 else "   ") + "\n" + parseNotes(s)

def mergeTabs(tab1, tab2):
	rows1 = tab1.split("\n")
	rows2 = tab2.split("\n")
	sum = ""
	for i in range(0, len(rows1)):
		if i < len(rows1):
			sum += rows1[i]
		if i < len(rows2):
			sum += rows2[i]
		sum += "\n"
	return sum.rstrip()

def splitRows(tab, charLength):
	result = remaining = ""
	rows = tab.split("\n")
	if len(rows[1]) < charLength:
		return tab.rstrip()
	index = rows[1][0:charLength].rfind('|')
	for i in range(0, len(rows)):
		result += rows[i][0:index + 1] + "\n"
		remaining += rows[i][index:] + "\n"
	return result.rstrip() + "\n\n" + splitRows(remaining, charLength)

if len(sys.argv) == 3:
	numberStrings = int(sys.argv[1])
	barline = barline(numberStrings)
	tab = barline
	for line in sys.stdin:
		if line[0] is ',':
			tab = mergeTabs(tab, barline)
		else:
			tab = mergeTabs(tab, tabColumn(line))
	print splitRows(tab, int(sys.argv[2]))
else:
	print "Usage: python %s <numberOfStrings> <charLimit>" % sys.argv[0]
