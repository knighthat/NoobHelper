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



<h2>
  <p align="center">
    <img src="https://i.ibb.co/fMb2KFg/Break-Assistant.png" alt="Break-Assistant" border="0">
  </p>
</h2>

Too tired with mining block by block? Don’t worry, I’m here to rescue. This feature allows you to break many blocks that have the same properties at once.

**And of course, everything is modifiable in *config.yml***

<details><summary> Example </summary>
    <img src="https://i.ibb.co/PG9WD43/Block-Break-Assistant.gif" alt="Block-Break-Assistant" border="0">
</details>



<h2>
  <p align="center">
    <img src="https://i.ibb.co/cDszbPH/Fast-Leaf-Decay.png" alt="Fast-Leaf-Decay" border="0">
  </p>
</h2>

Simply speed up the decaying process. No more waiting!

<details><summary> Example </summary>
    <img src="https://i.ibb.co/2n99p0c/Faster-Leaf-Decay.gif" alt="Faster-Leaf-Decay" border="0">
</details>



<h2>
  <p align="center">
    <img src="https://i.ibb.co/378TsFC/Smart-Harvest.png" alt="Smart-Harvest" border="0">
  </p>
</h2>

This one is my favorite; imagine you don’t have to break crops just to realize it’s not fully grown yet. With this plugin, just right-click and you’ll see MAGIC!

<details><summary> Example </summary>
    <img src="https://i.ibb.co/b3K1PD3/Smart-Harvest.gif" alt="Smart-Harvest" border="0">
</details>



<h2>
  <p align="center">
    <img src="https://i.ibb.co/XzS2Fz0/Equipment-Reload.png" alt="Equipment-Reload" border="0">
  </p>
</h2>

Here, my friend. One of the greatest features in this plugin. No longer go to inventory and search for the tool because yours just broke, my plugin can handle that in a blink of an eye

<details><summary> Example </summary>
  <img src="https://i.ibb.co/zJKSb3B/Tool-Replacement.gif" alt="Tool-Replacement" border="0">
</details>



<h2>
  <p align="center">
    <img src="https://i.ibb.co/J7yVstF/TrashBin.png" alt="TrashBin" border="0">
  </p>
</h2>

It can destroy everything, seriously! Even the Netherite items

<details><summary> Example </summary>
  <img src="https://i.ibb.co/jH38yk9/Trash-Bin.gif" alt="Trash-Bin" border="0">
</details>



<h2>
  <p align="center">
    <img src="https://i.ibb.co/cJ20dPp/Death-Chest.png" alt="Death-Chest" border="0">
  </p>
</h2>

Saves player's inventory when he/she dies. Inventory once created can only be retreived by the owner.
Lost items can be tracked by using `/noobhelper lostitems` command

<details><summary> Example </summary>
  <img src="https://i.ibb.co/WPwSmL7/Death-Chest.gif" alt="Death-Chest" border="0">
</details>

---



<p align="center">
    <img src="https://i.ibb.co/98NHjRQ/Commands.png" alt="Commands" border="0">
</p>



## `/noobhelper reload`

- Reload plugin
- Permission: **_noobhelper.command.reload_**

## `/noobhelper lostitems`
- See the nearest death chest

---



<p align="center">
    <img src="https://i.ibb.co/F00Pmkh/Permissions.png" alt="Permissions" border="0">
</p>


## `noobhelper.command.*`
- Wildcard to use all commands that are available

## `noobhelper.command.[command]`
- Required to execute command to open certain GUIs.

## `noobhelper.equipment_replacement`
- Requried to replace tool when it breaks.

## `noobhelper.smart_harvesting`
- Required to be able to harvest crops.

## `noobhelper.break_assistant`
- Required to perform multiple blocks breaking at once.

## `noobhelper.trash_bin.*`
- Wildcard for trash bin feature, gives player full control.

## `noobhelper.trash_bin.place`
- Required to create trash bin sign.

## `noobhelper.trash_bin.remove`
- Required to destroy trash bin sign.

## `noobhelper.trash_bin.use`
- Required to open/use by right-clicking the trash bin sign.

## `noobhelper.death_chest`
- Required to create Death Chest on Death.

---



<p align="center">
	<img src="https://i.ibb.co/pWqBqqJ/Config.png" alt="Config" border="0">
</p>
<details><summary> SEE FILE </summary>

```YML
version: 0.6.2

prefix: "&7[&aNoobHelper&7]"


equipment_replacement: true


death_chest:
  enabled: true
  death_message: "&cLast death locaton &4&c&lX:%x% Y:%z% Z:%z%&r&c."
  not_your_chest: "&eThis death chest &4&lDOES NOT &ebelong to you!"
  retrieved: "&a&lCongrats! &rYou just successfully retrieved all your lost items."
  retrieval_sound: true
  # Anything between two "%" will show to player
  # but player can click on it to review items.
  content_message: "&2Click %&b&l[HERE]% &2to review your lost items"
  container_not_exist: "&cThis container is no longer exist."
  # Title for GUI of "[HERE]" button above
  # %player% displays actual name, %display_name% shows their nick names
  container_title: "%player%'s inventory at &4&lX:%x% Y:%y% Z:%z%"
  exp_bottle_name: "&eExp: &a%exp%"
  no_previous_dead_location: "&eLooks like you're immortal, m8!"


smart_harvesting:
  enabled: true
  sound: true   


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
  add_delay: true
  # How long in ticks it has to wait
  # before breaking the next block.
  # 20 ticks / 1 second.
  delay: 1
  # Only work if add_delay is turned on.
  # Require version 1.16.4 and above
  add_effect: true
  


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
    <a href="http://fumacrom.com/3lNzk">
      <img src="https://i.ibb.co/9Vh33L6/Formatter.png" alt="Formatter" border="0">
    </a>
</p>