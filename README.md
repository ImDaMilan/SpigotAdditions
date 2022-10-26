# SpigotAdditions
An additional Spigot library aimed to be the dependency that saves developers time from pointless tasks and brings a bunch of useful utilities for Bukkit/Spigot/Paper developers to use!
--
This library was heavily inspired by RedLib and SpigotUtils, but it aims to provide more simple features and easier usage! Some of the great features of SpigotAdditions include:

 ### 1. An Event Listener + Handler annotation that does everything for you!

```
public static @Listener void onPlayerJoin(PlayerJoinEvent event) {  
    event.getPlayer().sendMessage("Hello!");  
}
```
No need to register the listener or put it in a class that implements Listener, you can put the annotation above any static method that's parameter is an event, and SpigotAdditions will automatically do everything else for you!

###  2. An @Command annotation that automatically registers itself,  the executor, and all the basic stuff you might need from it!
```
@Command(
 name = "test",   
 permission = "test.test",   
 usage = "/test",  
 description = "Testing command",  
 aliases = {"t", "testcommand"},  
 minArgs = 1,   
 playerOnly = false,  
 consoleOnly = false)  
)  
public class TestCommand {  
  public static boolean execute(CommandSender sender, Command command, String label, String[] args) {  
	  sender.sendMessage("Test command executed!");
	  return true;
 }}
```
Again, no need to put it in the plugin.yml or to do anything in the main class of the plugin, the annotation manages itself, there are also more parameters in the annotation that you can check out!

###  3. A Custom Enchantment API!
```
public EnchantmentWrapper(String key, String name, int maxLevel, boolean treasure, boolean cursed, EnchantmentTarget target, Enchantment... conflicts)
```
Above is the constructor that is used to create an EnchantmentWrapper instance.
```
public EnchantmentWrapper(Plugin plugin, String key, String name, int maxLevel, boolean treasure, boolean cursed, EnchantmentTarget target, Enchantment... conflicts)
```
The above can be used as well to register the enchantment to the plugin instead of a Minecraft enchantment.
```
new EnchantmentWrapper("telekinesis", "Telekinesis", 1, false, false, EnchantmentTarget.TOOL);
```
And this above is it! Yup, you can create a custom enchantment with a single line of code! This, combined with the Listener API mentioned above can be used to add functionality to your custom enchantments!
### 4. ItemStackBuilder
```
ItemStack item = new ItemStackBuilder(Material.BOOK)  
     .name("Test")  
     .lore("Test")  
     .amount(1)  
     .modelData(1)  
     .enchant(Enchantment.DURABILITY, 1);
```
There is a lot more to the ItemStackBuilder class, like adding persistent data tags with a single method, adding attributes, clearing all enchants, etc.

### 5. Easy GUI creation tools!
```
InventoryGUI iGui = new InventoryGUI("Test", 54);  
iGui.fillEmptySlots(new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE).name(""));  
iGui.addButtons(GUIButton.create(new ItemStackBuilder(Material.BOOK).name(ChatColor.RED + "Test"), event -> {  
  event.getWhoClicked().sendMessage("Test");  
}));
Inventory gui = iGui.getInventory();
```
This automatically makes all slots in the inventory unclickable except for the buttons and the ones that you say should be open in your code! It does all the GUI setting up for you, so you don't have to bother with that! There is **A LOT** more in the InventoryGUI class that you can use, you can discover all the amazing features by using the library!

### 6. Custom Item API and Player Head API!
```
new CustomItem(item, "plugin_name:test_custom_item", PLUGININSTANCE);
```
This will add data to the item (ItemStack) that is passed as the first parameter, effectively turning it into a custom item! To further increase it's capabilities, you can use the ItemStackBuilder and the Listener API. You can always check if an item is a custom item using:
```
CustomItem.isCustomItem(item, PLUGININSTANCE);
```
There is also a utility class for easily getting player heads as items!
```
ItemStack head = new PlayerHeadItem(player).get();
```

### 7. MySQL Tools!
```
SQLConnection sql = new SQLConnection(host, port, database, name, pass);
sql.execute("CREATE TABLE IF NOT EXISTS tablename");
sql.forEach("GET * FROM db", result -> System.out.println(result));
sql.getResults("GET * FROM db");
sql.getConnection();
sql.close();
```
And a lot more! Most of the queries are ran async, so you don't have to worry about that!

### 8. Updater API!
```
Updater updater = new Updater(PLUGININSTANCE, RESOURCEID);
String latestVersion = updater.getLatest();
updater.download(); //automatically downloads the newest version!
```

## Using SpigotAdditions in your own plugin!
To use SpigotAdditions, you can use it as a dependency (or softdepend) in your plugin:
```
depend: [ SpigotAdditions ]
```
### Maven
```
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```
```
<dependency>
	<groupId>com.github.ImDaMilan</groupId>
	<artifactId>SpigotAdditions</artifactId>
	<version>NEWEST</version>
	<scope>provided</scope>
</dependency>
```
### Gradle
```
repositories {
	maven { url 'https://jitpack.io' }
}
```
```
dependencies {
	implementation 'com.github.ImDaMilan:SpigotAdditions:Tag'
}
```
