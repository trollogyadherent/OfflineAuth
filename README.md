# Project status: it's not dead
## I resumed development not so long ago (refer to readme change date, 12/07). It turns out there is no easy way to get SSL (read: sending of credentials not in plaintext) and I am halfway through implementing encryption half by hand (of course using built in cryptographical functions). Currently, checking credentials, deleting account and registration are end to end encrypted. This makes the "easy API part" obsolete, you will still have administrative commands that can be used via rcon.
## I will push code when I am done with encryption and keypair authentification. Next steps will be finishing tokens and adding upload of skins.

# OfflineAuth 1.7.10 (Forge)
Early alpha Minecraft authentification system contained in a Forge mod.

![image](https://user-images.githubusercontent.com/19153947/159351094-b181a42b-220e-4fa0-943c-c34063652919.png)

### Motivation
Microsoft forcing people to migrate from Mojang to Microsoft and, frankly, this company is just plain evil. So yeah. (Also, the author does not believe in "intellectual property")

### Tell me more
This mod runs a small rest server (spark) alongside the main Minecraft process. This keeps everything contained to the server which could even run on a local network, independent of any external auth servers, such as `vanilla` or `ely.by`.

### Getting started
If you are a server oeprator:
* Drop mod jar into mods folder. Let it generate a config.
* Your hosting solution needs to provide you with the option to open additional ports. Pick a port and set it in the config (default: 4567). Minecraft and the rest server can not both listen on the same port.
* Tell your players about the port they need to input in order to be able to register accounts.

If you are a player:
* Drop mod jar into mods folder.
* Add server in multiplayer menu. Go to the "Manage auth" menu.
* Change the port if needed, and register an account. Afterwards you should be able to join the server.

### Features
* Registration and deletion of accounts, changing account password
* Config options to disallow registration

### Coming Features
* Commands to change certain config options
* Registration using one-time tokens (halfway done)
* Uploading and using skins
* Logging in via key file
* Raid mitigation

### Credits
* [TechnicianLP](https://github.com/TechnicianLP) for his [ReAuth](https://github.com/TechnicianLP/ReAuth) mod. I ripped off the config and GUI to adapt them to my mods needs.
* [SinTh0r4s](https://github.com/SinTh0r4s), [basdxz](https://github.com/basdxz), and [TheElan](https://github.com/TheElan) for their [ExampleMod 1.7.10](https://github.com/SinTh0r4s/ExampleMod1.7.10) and the included gradle buildscript.

### Building
`gradlew build` should do the trick.

### Contact
* [This project GitHub repo](https://github.com/trollogyadherent/OfflineAuth), create an issue if something is broken
* Matrix rooms `#boysgregified:matrix.thisisjoes.site` and `minecraft-modding-general:matrix.org`

### License
LGPLv3+SNEED
