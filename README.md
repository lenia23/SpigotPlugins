# SpigotPlugins

## TeleportExtensionPlugin
You can save locations as label list to teleport.
### Usage:
```sh
wget https://github.com/subterranean-eye/SpigotPlugins/releases/download/version1.1/TeleportExtensionPlugin-1.1.zip
unzip TeleportExtensionPlugin-1.1.zip
mv TeleportExtensionPlugin-1.1.zip plugins/
```

### Version
|Minecraft Version|Verified|
|-|-|
|1.17.x|〇|
|1.16.x|Unverified|
|1.15.x|Unverified|
|1.14.x|〇|
|~1.13.x|Unverified|

### Command example
```
# same as tp command
tpe tp 10 23 567

# add lavel
tpe add home 10 23 567
# home: [x: 10, y: 23, z: 567,world:nether] is added

# teleport with label
tpe tp home

# list
tpe tp list
# home: [x: 10, y: 23, z: 567,world:nether]

# update label
tpe update home 10 23 456

# remove label
tpe remove home

```
