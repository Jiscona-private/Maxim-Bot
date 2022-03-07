# Maxim-Bot
This is a repository for the Maxim bot. Maxim plays connect four 3D with the specifications of https://github.com/revol-xut/game_master_four_wins.

## Using the bot
If you want to use Maxim, you have to 
1. Install the gamemaster using the link above.
2. Build the gamemaster.
3. Get the `maxim_1-2.java` file in the "build" folder.
4. Execute Maxim using `java maxim_1-2.java`. Give the name of the file that Maxim is supposed to write his moves in as an argument right behind `java maxim_1-2.java` (e.g. as `java maxim_1-2.java black`).

After this, Maxim will automatically begin to play the game as soon as he has any commands in the file specified above.

## Search depth
Maxim is using a minixmax function to determine the best move. Thus, the "deeper" the bot searches, the better his moves will be - but it'll take more time, too. You can decide which depth to use. Change the 4 in line 299 (`minmax` call in the `getMove` function) to the value you want.
