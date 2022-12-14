
# SpigotAdditions
An additional Spigot library aimed to be the dependency that saves developers time from pointless tasks and brings a bunch of useful utilities for Bukkit/Spigot/Paper developers to use!
--
[![](https://jitpack.io/v/ImDaMilan/SpigotAdditions.svg)](https://jitpack.io/#ImDaMilan/SpigotAdditions)

This library was heavily inspired by RedLib, Spiglin, SpigotUtils, but it aims to provide more simple features and easier usage! Some of the great features of SpigotAdditions include:

 ### 1. An Event Listener + Handler annotation that does everything for you!

```java
public static @Listener void onPlayerJoin(PlayerJoinEvent event) {  
    event.getPlayer().sendMessage("Hello!");  
}
```
No need to register the listener or put it in a class that implements Listener, you can put the annotation above any static method that's parameter is an event, and SpigotAdditions will automatically do everything else for you!

###  2. An @Command annotation that automatically registers itself,  the executor, and all the basic stuff you might need from it!
```java
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
```java
private EnchantmentWrapper(String key, String name, int maxLevel, boolean treasure, boolean cursed, EnchantmentTarget target, Enchantment... conflicts)
```
Above is the constructor that is used to create an EnchantmentWrapper instance.
```java
private EnchantmentWrapper(Plugin plugin, String key, String name, int maxLevel, boolean treasure, boolean cursed, EnchantmentTarget target, Enchantment... conflicts)
```
The above can be used as well to register the enchantment to the plugin instead of a Minecraft enchantment.
```java
Enchantment TELEKINESIS = EnchantmentWrapper.createEnchantment("telekinesis", "Telekinesis", 1, false, false, EnchantmentTarget.TOOL);
```
And this above is it! Yup, you can create a custom enchantment with a single line of code! This, combined with the Listener API mentioned above can be used to add functionality to your custom enchantments!
### 4. ItemStackBuilder
```java
ItemStack item = new ItemStackBuilder(Material.BOOK)  
     .name("Test")  
     .lore("Test")  
     .amount(1)  
     .modelData(1)  
     .enchant(Enchantment.DURABILITY, 1);
```
There is a lot more to the ItemStackBuilder class, like adding persistent data tags with a single method, adding attributes, clearing all enchants, etc.

### 5. Easy GUI creation tools!
```java
InventoryGUI iGui = new InventoryGUI("Test", 54);  

iGui.fillEmptySlots(new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE).name(""));  

iGui.addButtons(GUIButton.create(new ItemStackBuilder(Material.BOOK).name(ChatColor.RED + "Test"), event -> {  
  event.getWhoClicked().sendMessage("Test");  
}));

Inventory gui = iGui.getInventory();
```
This automatically makes all slots in the inventory unclickable except for the buttons and the ones that you say should be open in your code! It does all the GUI setting up for you, so you don't have to bother with that! There is **A LOT** more in the InventoryGUI class that you can use, you can discover all the amazing features by using the library!
```java
PaginatedGUI gui = new PaginatedGUI();

gui.addPage(iGui);
gui.nextPage(player);
gui.previousPage(player);

gui.getCurrentPage();
gui.getPages().forEach(page -> {
	page.addButtons(new SetPageButton(item, gui, SetPageButtonType.NEXT), new SetPageButton(item, gui, SetPageButtonType.PREVIOUS));
}
```
Introduced in version 0.2, PaginatedGUIs can also be created, and they are as simple to create and use as one can imagine, 0.2 also introduced SetPageButtons, that are GUIButtons that willl automatically do all the PaginatedGUI page setting for you!

### 6. Custom Item API and Player Head API!
```java
ItemStack customItem = CustomItem.from(item, "plugin_name:test_custom_item", PLUGININSTANCE);
```
This will add data to the item (ItemStack) that is passed as the first parameter, effectively turning it into a custom item! To further increase it's capabilities, you can use the ItemStackBuilder and the Listener API. You can always check if an item is a custom item using:
```java
CustomItem.isCustomItem(item, PLUGININSTANCE);
```
There is also a utility class for easily getting player heads as items!
```java
ItemStack head = new PlayerHeadItem(player).get();
```

### 7. MySQL Tools!
```java
SQLConnection sql = new SQLConnection(host, port, database, name, pass);
sql.execute("CREATE TABLE IF NOT EXISTS tablename");
sql.forEach("GET * FROM db", result -> System.out.println(result));
sql.getResults("GET * FROM db");
sql.getConnection();
sql.close();
```
And a lot more! Most of the queries are ran async, so you don't have to worry about that!

### 8. MongoDB Tools/API
```java
MongoConnection mongo = new MongoConnection("mongo-db-url", "db-name");
mongo.createCollection("collection-name");
String playerName = mongo.getString("collection-name", "playerName");
int playerKills = mongo.getInt("collection-name", "playerKills");
mongo.dropCollection("collection-name");
```
And a bunch of other useful Mongo-interaction methods! The above example works for any type of document, but you can also use your own POJOs as data archetypes for saving data on the Mongo database! An example is below!
```java
MongoConnectionPOJO<Profile> mongoProfiles = new MongoConnectionPOJO("mongo-db-url",  "db-name", "collection-name", Profile.class);

Profile profile = new Profile(player.getUniqueId());
mongoProfiles.insertOne(profile);

List<Profile> profiles = new ArrayList<>();
mongoProfiles.into(profiles); //will insert all profile data from the database into the profiles variable
List<Profile> profiles = mongoProfiles.getAll(); //you can also define the limit and skip if needed
```

### 9. Updater API and Version API!
```java
Updater updater = new Updater(PLUGININSTANCE, RESOURCEID);
String latestVersion = updater.getLatest();
updater.download(); //automatically downloads the newest version!

String version = BukkitUtils.getServerVersion();
Class<?> clazz1 = BukkitUtils.getNMSClass("MinecraftServer");
Class<?> clazz2 = BukkitUtils.getOBCClass("CraftWorld");
```
It's important not to confuse the Updater's version methods with the BukkitUtils', as the first one refers to the versions of the plugin itself, whilst the second one to the Minecraft server version! BukkitUtils were implemented in 1.0-RC1 (so they will not work in versions below).

### 10. Serealizers!
To create your own Serealizers, you can implement the `JSONSerealizer<>` and/or `YAMLSerealizer<>` interfaces in your own class!
SpigotAdditions has a built-in ItemSerealizer class since 0.3, that you can use to serealize ItemStacks to both JSON and YAML!
```java
ItemSerealizer is = new ItemSerealizer();
ItemStack itemFromJson = is.fromJson(jsonString, PLUGININSTANCE);
String jsonFromItem = is.toJson(item);
ItemStack itemFromYaml = is.fromYaml(yamlString, PLUGINSTANCE);
String yamlFromItem = is.toYaml(item);
```

### 11. Util Classes!
Introduced in 1.0-RC1, these classes are here to ease up your life and get rid of all that ugly boilerplate code!
```java
PlayerUtils.sendActionBar(player, "Hello!");
PlayerUtils.hidePlayer(player) //will hide/vanish the selected player
PlayerUtils.banPlayer(player, "Ban Reason");
PlayerUtils.heal(player);
PlayerUtils.feed(player);
PlayerUtils.setMaxHealth(player, 30);
Block blockBelow = PlayerUtils.getBlockBelow(player);
PlayerUtils.sendPacket(player, PACKET);
UUID uuid = PlayerUtils.getOfflinePlayerUUID(playerName);
```
And many more player util methods! The `showPlayer()` and `hidePlayer()` methods are even tied to a Listener, so that joining and leaving players won't bug out and see or unneedingly not see the player!
```java
ItemUtils.setTag(item, "persistent-tag-key", "persistent-value", PLUGININSTANCE, PersistentDataType.STRING);
ItemUtils.addLore(item, "Added lore to existing item!");
ItemUtils.setName(item, "Sword of Testing");
ItemUtils.setCustomModelData(item, 1);
ItemUtils.setUnbreakable(true);
ItemUtils.setDurability(item, 100);
ItemUtils.clearPotionEffects(item);
ItemUtils.setBookPages(item, "Page 1");
ItemUtils.setBookAuthor(item, "ImDaMilan");
ItemUtils.isArmor(item);
ItemUtils.isTool(item);
ItemUtils.isFood(item);
ItemUtils.isCrop(item);
```
And many, many more! There are methods for modifying any type of ItemMeta - Books, Fireworks, Potions, Attributes, Enchants, ItemFlags, and so on! This is by far the longest Util class (and the above showcase is not even half of them!).
```java
BukkitUtils.runAsync(() -> System.out.println("This is ran async!"));
BukkitUtils.getAsync(object, () -> object.setName("test")); //this is used for returning values async, not usually possible in async functions, could be great for returning SQL data for example, the value is returned through the object variable as a CompletableFuture
BukkitUtils.isVersionHigherThan("Selected MC version");
BukkitUtils.runTimerAsync(() -> System.out.println("This is ran async!"), 0L, 10L);
double serverTPS = BukkitUtils.getTPS();
String withColors = BukkitUtils.getColorCoded("$cWith a custom colorcode prefix!", '$');
```
More of these are shown above as part of the Version API!
Plans for the future are to add more util classes, like EntityUtils, WorldUtils, etc.

### 13. Config and DataFile APIs!
You can use SpigotAdditions' Config API, introduced in 1.1, to save classes with static fields to a config file and easily manipulate those fields and file without messing with config classes and Spigot's YamlConfiguration and so on.
```java
public @Config class MainConfig {
    private static @Path("welcome-message") String welcomeMessage = "Welcome";
    private static @Path String leaveMessage = "Goodbye!";
}

ConfigManager.saveToFile(PLUGININSTANCE, MainConfig.class);
```
The above will create the following structure in the config.yml file:
```yaml
welcome-message: Welcome
leaveMessage: Goodbye!
```
You can set which file you want to save the config to by passing it to the Config annotation, `@Config("test.yml")`, and keep in mind that if the Path annotation is empty, it the config member takes the name of the field, otherwhise, it takes the name of the parameter passed to it!

SpigotAdditions also supports a similar way of saving data to a YAML file.
```java
public @DataFile("playerdata.yml") class PlayerProfile {

    private @ObjectKey String playerName;
    private @Path int level;
    private @Path int strength;
    private @Path int dexterity;
    public static ArrayList<PlayerProfile> objects = new ArrayList<>();

    public PlayerProfile() {}

    public PlayerProfile(String playerName, int level, int strength, int dexterity) {
        this.playerName = playerName;
        this.level = level;
        this.strength = strength;
        this.dexterity = dexterity;
        objects.add(this);
    }

    public static ArrayList<PlayerProfile> getObjects() {
        return objects;
    }
}

ConfigManager.saveToFile(PLUGININSTANCE, PlayerProfile.class);
ArrayList<PlayerProfile> profiles = ConfigManager.getFromFile(PLUGININSTANCE, PlayerProfile.class);
```
There's a lot to unpack here, so let's start off:

 1. The class must have the `@DataFile("file-path.yml")` annotation.
 2. The class must have only 1 `@ObjectKey` annotated field, that separates the objects in the file.
 3. The `@Path` works the same as in the Config API.
 4. The class must have an empty no-args constructor.
 5. The class must include a static `getObjects()` method that returns all objects instantiated.

An example of the output of a `playerdata.yml` file would be:
```yaml
ImDaMilan:
    level: 100
    strength: 100
    dexterity: 150
Player2:
    level: 50
    strength: 10
    dexterity: 100
ThirdPlayer:
    level: 50
    strength: 10
    dexterity: 100
```
In the example above, we have 3 objects, where the object keys / names of the players are ImDaMilan, Player2 and ThirdPlayer, the `level` variables are 100, 50 and 50, and so on.

## Using SpigotAdditions in your own plugin!
To use SpigotAdditions, you can use it as a dependency (or softdepend) in your plugin:
```yml
depend: [ SpigotAdditions ]
```
### Maven
For the latest stable release use:
```xml
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```
```xml
<dependency>
	<groupId>com.github.ImDaMilan</groupId>
	<artifactId>SpigotAdditions</artifactId>
	<version>1.1.4</version>
	<scope>provided</scope>
</dependency>
```
For the latest development build use:
```xml
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```
```xml
<dependency>
	<groupId>com.github.ImDaMilan</groupId>
	<artifactId>SpigotAdditions</artifactId>
	<version>1.2-beta.1</version>
	<scope>provided</scope>
</dependency>
```
Keep in mind that development builds are not production-ready and should not be used outside of testing environments until the official release for those features appear!
### Gradle
For the latest stable release use:
```groovy
repositories {
	maven { url 'https://jitpack.io' }
}
```
```groovy
dependencies {
	implementation 'com.github.ImDaMilan:SpigotAdditions:1.1.4'
}
```
For the latest development build use:
```groovy
repositories {
	maven { url 'https://jitpack.io' }
}
```
```groovy
dependencies {
	implementation 'com.github.ImDaMilan:SpigotAdditions:1.2-beta.1'
}
```
Keep in mind that development builds are not production-ready and should not be used outside of testing environments until the official release for those features appear!
## Additional Credits
Big thank you to [HSGamer](https://www.spigotmc.org/members/hsgamer.248240/) on Spigot for helping with the command syncing for the Command annotation!
