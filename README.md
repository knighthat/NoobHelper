<p align="center">
    <a href="https://paypal.me/TnKnightN">
      <img src="https://i.ibb.co/nrWwjZq/Buy-Me-Coffe.png" alt="Buy-Me-Coffe" border="0">
    </a>
</p>

---



<p align="center">
        <img src="https://i.ibb.co/B6FtmYr/Description.png" alt="Description" border="0">
</p>

<span>In order for noobs/newbies to approach the game that is growing so fast by changing some logic to make their gameplay a little less frustrated. FYI, these features not only for new comers, a dude with almost 8 years of game experience still see this one convenient.

---



<p align="center">
    <img src="https://i.ibb.co/Wkd1DYJ/Requirements.png" alt="Requirements" border="0">
</p>


<p align="center"> JVM <b>11</b> and above </p>


<p align="center"> <b>Server version at least 1.14.4 </b> </p>
<p align="center"> *Runnable on 1.13.x but no supports provided if any issue occurs </p>

---



<p align="center">
    <img src="https://i.ibb.co/GHFzSg5/Features.png" alt="Features" border="0">
</p>



## GUI Shortcuts:

You don’t have to place these blocks down in order to get access to these GUIs anymore. But still, permission `noobhelper.command.[command]` is required. 

**Can be turned off in *config.yml***

<details><summary> Open Workbench </summary>
   <img src="https://i.ibb.co/xMGkJMW/Workbench-Command.gif" alt="Workbench-Command" border="0">
</details>

<details><summary> Open Anvil </summary>
    <img src="https://i.ibb.co/R3pRmQq/Anvil-Command.gif" alt="Anvil-Command" border="0">
</details>

<details><summary> Open Cartography </summary>
    <img src="https://i.ibb.co/PNTX8Mn/Cartography-Command.gif" alt="Cartography-Command" border="0">
</details>

<details><summary> Open Grindstone </summary>
    <img src="https://i.ibb.co/5MWM431/Grindstone-Command.gif" alt="Grindstone-Command" border="0">
</details>

<details><summary> Open Loom </summary>
    <img src="https://i.ibb.co/mX9hpkH/Loom-Command.gif" alt="Loom-Command" border="0">
</details>

<details><summary> Open Smiting </summary>
    <img src="https://i.ibb.co/kQd6DKR/Smithing-Table-Command.gif" alt="Smithing-Table-Command" border="0">
</details>

<details><summary> Open Stonecutter </summary>
    <img src="https://i.ibb.co/nDd6wfp/Stonecutter-Command.gif" alt="Stonecutter-Command" border="0">
</details>





## Block Break Assistant:

Too tired with mining block by block? Don’t worry, I’m here to rescue. This feature allows you to break many blocks that have the same properties at once.

**And of course, everything is modifiable in *config.yml***

<details><summary> Example </summary>
    <img src="https://i.ibb.co/PG9WD43/Block-Break-Assistant.gif" alt="Block-Break-Assistant" border="0">
</details>





## Fast Leaf Decay:

Simply speed up the decaying process. No more waiting

<details><summary> Example </summary>
    <img src="https://i.ibb.co/2n99p0c/Faster-Leaf-Decay.gif" alt="Faster-Leaf-Decay" border="0">
</details>

 



## Smart Harvest:

This one is my favorite; imagine you don’t have to break crops just to realize it’s not fully grown yet. With this plugin, just right-click and you’ll see MAGIC!

<details><summary> Example </summary>
    <img src="https://i.ibb.co/b3K1PD3/Smart-Harvest.gif" alt="Smart-Harvest" border="0">
</details>

 



## Tool Replacement:

Here, my friend. One of the greatest features in this plugin. No longer go to inventory and search for the tool because yours just broke, my plugin can handle that in a blink of an eye

<details><summary> Example </summary>
  <img src="https://i.ibb.co/zJKSb3B/Tool-Replacement.gif" alt="Tool-Replacement" border="0">
</details>





## Trash Bin:

It can destroy everything, seriously! Even the Netherite items

<details><summary> Example </summary>
  <img src="https://i.ibb.co/jH38yk9/Trash-Bin.gif" alt="Trash-Bin" border="0">
</details>

---



<p align="center">
    <img src="https://i.ibb.co/98NHjRQ/Commands.png" alt="Commands" border="0">
</p>



### `/noobhelper reload`

- Reload plugin
- Permission: **_noobhelper.command.reload_**

---


<p align="center">
    <img src="https://i.ibb.co/F00Pmkh/Permissions.png" alt="Permissions" border="0">
</p>


### `noobhelper.command.[command]`
- Required to execute command to open certain GUIs.

### `noobhelper.equipment_replacement`
- Requried to replace tool when it breaks.

### `noobhelper.smart_harvesting`
- Required to be able to harvest crops.

### `noobhelper.break_assistant`
- Required to perform multiple blocks breaking at once.

### `noobhelper.trash_bin.place`
- Required to create trash bin sign.

### `noobhelper.trash_bin.remove`
- Required to destroy trash bin sign.

### `noobhelper.trash_bin.use`
- Required to open/use by right-clicking the trash bin sign.

---


<p align="center">
	<img src="https://i.ibb.co/pWqBqqJ/Config.png" alt="Config" border="0">
</p>
<details><summary> SEE FILE </summary>

```YML
version: 0.4

prefix: "&7[&aNoobHelper&7]"

equipment_replacement: true
smart_harvesting:
  enabled: true
  sound: true

# Permission to use commands: noobhelper.commmand.[command]    
command_shortcuts:
  require_permission: true
  aliases:
    anvil:
      - caide
    workbench:
      - banchetao
    cartography:
      - banve
    grindstone:
      - maymai
    loom:
      - maydet
    stonecutter:
      - maycat
    smithing:
      - banren
    
    
break_assistant:
  enabled: true
  requirements: 
    sneaking: true
    # Permission: noobhelper.break_assistant
    permission: false
    survival_mode: true
  # This argument only be fulfilled only if player's food bar 
  # and item's durability can keep up
  max_block: 200
  food_consumption:
    enabled: true
    rate: 0.1
  apply_damage: true 
  
  
fast_leaf_decay:
  enabled: true
  # The higher the number, the longer the process.
  slowness: 1    
  add_effect: true


# Permission to use trash bin feature: noobhelper.trash_bin.use
# Permission to place sign: noobhelper.trash_bin.place
# Permission to destroy sign: noobhelper.trash_bin.remove  
trash_bin:
  enabled: true
  title: "&4&lWARNING: &eAll items will be deleted!!!"
  message: "You've created a trash bin!"
  lines:
    - "&c&l[Trash Bin]"
    - ""
    - ""
    - "&aRight click to use"
    
    
#
#
#         GENERAL MESSAGES
#
#

reload: "&aAll files had been reloaded!"
player_reload: "%player% issued reload command!"
no_permission: "&cYou don't have permission %perm% to do this!"
```

</details>

---


<p align="center">
	<img src="https://i.ibb.co/4N0zFHK/Block-Data.png" alt="Block-Data" border="0">
</p>

<details><summary> SEE FILE </summary>

```YML
#
#  !WARNING! PLEASE DO NOT APPLY ANY CHANGES TO THIS FILE. 
#  
#  You are responsible for any potential issues happen to your
#  server if any modification is applied by human.
#
```

</details>