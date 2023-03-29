
# dodgeball-minigame

This is a simple Bukkit minigame plugin based on Dodgeball.

The functionality of the game is not completely alike to Dodgeball, instead it works with a unlimited gun that throws balls and you should dodge it to prevent getting eliminated.

The plugin allows you to create many game arenas on a single-server, making it able to have many players playing at the same time and same server.
## Modules

[core](https://github.com/stooneg0mes/dodgeball-minigame/tree/master/core) - This module is the abstract part of the game in general.

[plugin](https://github.com/stooneg0mes/dodgeball-minigame/tree/master/plugin) - This module is the implementation of the [core](https://github.com/stooneg0mes/dodgeball-minigame/tree/master/core) module above.
## Placeholders

This plugin supports [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)! These are the currently implemented placeholders:

`%game_total_kills%`, `%game_monthly_kills%` and `%game_weekly_kills%`

`%game_total_deaths%`, `%game_monthly_deaths%` and `%game_weekly_deaths%`

`%game_total_wins%`, `%game_monthly_wins%` and `%game_weekly_wins%`

`%game_total_defeats%`, `%game_monthly_defeats%` and `%game_weekly_defeats%`
## FAQ

#### What is the logic when the player enters the game on early stage?

When a player enters the `Waiting for Players` stage he will not have a pre-selected team,  he can select the team on his own (that way being able to team up with other friends) or choose not to. All players that haven't picked a team will receive a team on `Starting Game` stage, always selecting the less players team.

#### What is the logic to start the game?

The game has a minimum amount of players `(2)` and a maximum amount of players `(20)`. It will go to `Starting Game` stage when the teams are on a even amount of players. If the amount of players returns to an odd amount of players, it will go back to the `Waiting for Players` stage.

#### What happens when no one ever wins or lose the game?

When the game starts, it will have an end timer. The players can win/lose the game by themselves by eliminating other players, but, if no one wins/lose the game until the end timer stops, the game will pick a winner itself.

#### What happens when a game ends?

When a game ends, it will reset automatically going back to the early stage `Waiting for Players` and that way being available to play again. The players will return to the main lobby.
