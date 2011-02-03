=Introduction=
*[http://en.wikipedia.org/wiki/Mao_%28game%29 Mao]* is a card game where one player called the "dictator" can make up games. The basic rules are the same as [http://en.wikipedia.org/wiki/One_Card_%28game%29 One Card] or [http://en.wikipedia.org/wiki/Uno_%28game%29 Uno].

This is a text-based implementation of Mao in Java. The rules are added using [http://www.python.org/ Python] which is embedded in the game code. Players can add rules during runtime by typing in Python code when appropriate. The Python code is embedded using [http://www.jython.org/ Jython].

This was created by undergraduates at the University of Illinois at Urbana-Champaign.

=Rule Creation=
Rules are created using Python scripts. The game will prompt you to write the body segment of a function which will return True if something is against the rule.
{{{
>> [I] Write the body a Python function that will return True if it is against your rule.
>> [I] You may also import modules by typing "import [modulename]" and hitting enter twice.
<< [Q] You may write here: 
}}}
For example, if you want a rule that prevents you from playing even numbered cards if the sum of the hour and minute of time is prime, you could enter this:
{{{
possible_primes = ['2', '3', '5', '7', '11', '13', '17', '19', '23', '25', '29', '31', '37', '41', '43', '47', '53', '59', '61', '67', '71', '73', '79', '83']
hour = datetime.now().hour
minute = datetime.now().minute
sum_is_prime = str(hour + minute) in possible_primes
even_numbers = ['TWO', 'FOUR', 'SIX', 'EIGHT', 'TEN', 'QUEEN']
card_number_is_even = $current_number in even_numbers
return (card_number_is_even and sum_is_prime)
}}}
==Substitutions==
If you look carefully at the code above, you might notice an awkward variable
{{{
$current_number
}}}
This is not valid Python code. However, our game parses these value as follows:
||Substitution||Content||
||$current_number||The number of the card the player is trying to play||
||$current_suit||The suit of the card the player is trying to play||
||$current_color||The color of the card the player is trying to play||
||$last_number||The number of the card that was previously played and is now in the discard pile||
||$last_suit||The suit of the card that was previously played and is now in the discard pile||
||$last_color||The color of the card that was previously played and is now in the discard pile||
Before the Python code is evaluated to see whether the card played is against the rules or not, the game substitutes these variables in with proper values such as "SPADES" or "ACE" allowing the dictator to create rules with ease.
