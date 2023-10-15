# TASmod
A Tool-Assisted Speedrun-Mod for Minecraft 1.16.1.  
This mod is able to capture inputs and play them back, by pretending to be a keyboard.

> # ! Important !
>We are currently shifting over to **[Fabric 1.16.1](https://legacyfabric.net/)**. The development branch used to work with **Legacy Fabric 1.12.2** and now works with **Fabric 1.16.1** while the release branch is still on **Forge 1.12.2**
>
>This means all Alpha Releases currently only work for Forge 1.12.2 and the latest Beta Release only works for Legacy Fabric 1.12.2.
>
>Future Beta releases will be on Fabric 1.16.1

# Credits
Author of the original mod: [tr7zw](https://github.com/tr7zw/MC-TASmod)  
Main Author: Scribble

Contributions by: famous1622, Pancake

Tickratechanger: [Guichaguri](https://github.com/Guichaguri/TickrateChanger)  
Tickrate 0 idea: [Cubitect](https://github.com/Cubitect/Cubitick)  
Savestate idea: [bspkrs, MightyPork](https://github.com/bspkrs-mods/WorldStateCheckpoints), although implementation is totally different now

Special thanks: Darkmoon, The Minecraft TAS Community

# Features
*this section is being reworked*

# Development
## Setup
1. Clone this repository
2. Open/Import the gradle project
    - Use gradle version 8.4 or higher
    - Use [JDK 21](https://aws.amazon.com/corretto/) or higher
3. Run gradle tasks `genSources` then `eclipse` (instead of `eclipse`, you can simply open the project in IntelliJ)

## Running
If you're on eclipse, running `eclipse` should've generated to launch configs: `TASmod_Client.launch` and `TASmod_Server.launch`. Select it, then click the run or debug button in your IDE.  
On IntelliJ, run the gradle task `runClient` or `runServer` to launch the game.

> **Note:** Additional setup is required for the server to actually start, like changing the eula.txt and setting `online-mode` to false in server.properties.

## Building
Build the mod using the gradle task `remapJar`
