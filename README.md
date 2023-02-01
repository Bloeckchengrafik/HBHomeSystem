# HBHomes

This is a Home System for the HorstBlocks Minecraft Server.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Outlook](#outlook)
- [Credits](#credits)

## Installation

Before you can use this plugin, you need to make sure that you have the following packages installed:

1. Java 16
2. Maven >= 3.8.1
3. Paper (or spigot) 1.19.3
4. Docker or Podman (for development)

Now you can clone the repository and build the plugin with maven:

```bash
git clone https://github.com/Bloeckchengrafik/HBHomeSystem.git
# or with ssh
git clone git@github.com:Bloeckchengrafik/HBHomeSystem.git

cd HBHomeSystem
mvn clean package
```

Your plugin is now located in the `target` folder.

## Usage

To use this plugin, you need to install it on your server. You can do this by copying the jar file into the `plugins`
folder of your server.
After it is installed, it'll create a database configuration file in the `plugins/Homes` folder. You can configure the
database connection there.
If you happen to use the docker-compose file, you can safely ignore this step. Next, restart your server and you're good
to go.

There are some commands you can use to manage your homes:

- `/home <name>` - Teleports you to a home
- `/home` - Opens a GUI to select a home
- `/sethome <name>` - Sets a home at your current location
- `/home <player>:<name>` - Teleports you to a home of another player
- `/home <player>:` - Opens a GUI to select a home of another player
- `/sethome <player>:<name>` - Sets a home at your current location for another player

To regulate the usage of this plugin, you can use the following permissions:

- `homes.home` - Allows you to use the `/home` command
- `homes.sethome` - Allows you to use the `/sethome` command
- `homes.max.<number>` - Allows you to set `<number>` homes (maximal 255, every number above 255 will be set to
  infinity)
- `homes.others` - Allows you to view and teleport to homes of other players
- `homes.set.others` - Allows you to set homes for other players
- `homes.edit` - Allows you to edit the homes of other players - including deleting them

If you don't care about the permissions, you can use the `homes.*` permission to give all permissions at once.

## Outlook

In the future, one could add the following features:

### Optimizing Database Queries

Currently, the plugin uses a lot of queries to the database. This could be optimized by using caching using Redis.
This could happen in the `de.horstblocks.homes.db.HomeDAO` and `de.horstblocks.homes.db.PlayerDAO` classes by always
checking the cache first and invalidating it once a change is made or a timeout is reached.

### Multi-Server Support

Currently, the plugin only supports one server. If you want to use it on multiple servers, one would want to write
another plugin that synchronizes the data between the servers and reacts to a plugin channel or a message queue like
RabbitMQ or Kafka to teleport players to the correct server when they try to teleport to a home on another server.

## Credits

This Plugin is made for the HorstBlocks Minecraft Server.
