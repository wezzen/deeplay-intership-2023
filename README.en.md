![GitHub top language](https://img.shields.io/github/languages/top/wezzen/deeplay-intership-2023)
![GitHub language count](https://img.shields.io/github/languages/count/wezzen/deeplay-intership-2023)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/wezzen/deeplay-intership-2023)
![GitHub repo size](https://img.shields.io/github/repo-size/wezzen/deeplay-intership-2023)

![GitHub](https://img.shields.io/github/license/wezzen/deeplay-intership-2023)
![GitHub last commit](https://img.shields.io/github/last-commit/wezzen/deeplay-intership-2023)

<p align="left">
<img src="https://visitor-badge.laobi.icu/badge?page_id=wezzen.deeplay-intership-2023" alt="visitors"/>
</p>

Read in other languages: [Русский](README.md)

# Online game "go"

This project is an implementation of a game in the Java programming language using a client-server system.
design. The game features a variety of custom play options where players can compete against each other in
Go games.

<a name="Content"></a>

## Content

1. [Description](#Description)
2. [Game rules](#Game-rules)
3. [Project structure](#Project-structure)
4. [Architecture](#Architecture)
5. [Protocol](#Protocol)

<a name="Description"></a>

## Description

The game of Go is an ancient two-player strategy board game in which players take turns placing stones on
intersection of the lines of the playing field. The goal of the game is to capture territories and remove obstacles.
This project
The Go game was left without playing over the network through a client-server architecture.

<a name="Game-rules"></a>

## Game rules

### Game board

The game board in Go is a square measuring 19x19 cells (traditionally a board). Boards were also used
13x13 or 9x9 cells for larger parties.

### Stones

Players use two different colors of chips (black and white). Blacks are usually the first to leave.
Nearby stones require a group. Please note that the stones are connected only along the marked horizontal and
vertical lines.
The stones do NOT connect diagonally!

### Moves

Two players (black and white) go into line. We make the first move black.
During his turn, a player can place one of his pieces on empty cells on the board.
For each execution, one stone is placed on the board. The stone appears at the intersection of the line.
Players place stones on the board to fence off as much territory as possible. Stones are a kind of building material
material for building walls.
Once placed, a stone never moves!

### Capture

Each stone placed on the board has a `breath` - these are adjacent empty points on the board directly connected to the
stone
information. A stone in the center has 4 movements, on the edge - 3, and in a closet - 2. If all points of movement of a
stone are blocked, then it
removed from the board. Each captured stone produces one extra point!

When tiles of one color surround a group of tiles of another color and on top (horizontally or vertically), the group of
tiles
the enemy is captured and removed from the board.

The situation when there is one move left before capturing a stone or group of stones is called `atari`.

### Unacceptable actions

* Prohibition on suicide. You cannot place a stone in a position where it will be immediately grabbed.
* You cannot repeat the continuation position. It's called the ko rule, and it's as logical and necessary as
  prohibition.
  to suicide.

### End of the game

The game ends when both players in the queue miss their turn or when both players agree to end the game.
After the end of the game, points are counted.

### Scoring

Points are tallied at the end of the game. The player receives one point for each of his pieces on the board and
additional points for
the captured pieces and the territory he captured.

Since Black starts the game first, he has a slight advantage: he is the first to capture the corners, the first to
attack and
etc. And in order to equalize the initial conditions of the players, there is a rule according to which 5.5 points are
added to white.
time. Half a point will not allow the game to end in a draw. This compensation is called `komi`.

[To contents](#Content)

<a name="Project-structure"></a>

## Project structure

Main projects modules:

* client - client launcher
* dao - contains the application layer, which is associated with data transfer to the database
* dto - a module with classes that takes into account data transfer between application layers
* Decision Maker - contains modules taken into account for user inputs
* exceptions - contains custom exception modules
* game - module with game logic classes
* model - a module with possible game entities
* selfplay - module for local launch of games between bots
* server - server launch module
* ui - contains user interface display modules

[To contents](#Content)

<a name="Architecture"></a>

## Architecture

The project is implemented as a client-server structure:

Server: processing requests from clients, managing game sessions, rules for supporting games and interaction between them.
players.

Client: Interacts with the server, displays the playing field, allows players or a bot to make moves and monitor progress.
games.

[To contents](#Content)

<a name="Protocol"></a>

## Protocol

### Registration on the server

Request

```json
{
  "login": "login",
  "password-hash": "password_hash"
}
```

Response

```json
{
  "status": "success",
  "message": "You was successfully registrated!"
}
```

```json
{
  "status": "failure",
  "message": "This login already exists!", "Incorrect login!"
}
```

---

### Authorization on the server

Request

```json
{
  "login": "login",
  "password-hash": "password_hash"
}
```

Response

```json
{
  "status": "success",
  "message": "You entered!",
  "token": "UID"
}
```

```json
{
  "status": "failure",
  "message": "Invalid login or password!"
}
```

### Logout

Request

```json
{
  "token": "UID"
}
```

Response

```json
{
  "status": "success",
  "message": "You've log out!"
}
```

```json
{
  "status": "failure",
  "message": "You're not authorized!", "Something bad happened!"
}
```

---

### Create the game

Request

```json
{
  "with-bot": true, false,
  "color": "WHITE", "BLACK", "EMPTY",
  "size": 9, 13, 19,
  "token": "UID"
}
```

Response

```json
{
  "status": "success",
  "message": "Have a good game!",
  "game-id": "game_id"
}
```

```json
{
  "status": "failure",
  "message": "You're not authorized!", "Server is temporarily unavailable!"
}
```

---

### Join the game

Request

```json
{
  "game-id": "game_id",
  "token": "UID",
  "color": "WHITE", "BLACK"
}
```

Response

```json
{
  "status": "success",
  "message": "Have a good game!"
}
```

```json
{
  "status": "failure",
  "message": "You're not authorized!", "There are two players already!", "Server is temporarily unavailable!"
}
```

---

### During the play

### Make move

Request

```json
{
  "token": "UID",
  "answerType": "TURN",
  "row": "row",
  "column": "column"
}
```

Response

```json
{
  "status": "success",
  "message": "You can move!",
  "game-field": "game_field"
}
```

```json
{
  "status": "failure",
  "message": "Invalid move!", "Something bad happened!",
  "game-field": "game_field"
}
```

---

### Pass

Request

```json
{
  "token": "UID",
  "answerType": "PASS",
  "row": "row",
  "column": "column"
}
```

Response

```json
{
  "status": "success",
  "message": "You can pass!",
  "game-field": "game_field"
}
```

```json
{
  "status": "failure",
  "message": "Something bad happened!",
  "game-field": "game_field"
}
```
