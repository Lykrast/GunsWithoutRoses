# Guns Without Roses

Adds some simple Terraria-like guns and bullets to go with them.

[Modrinth](https://modrinth.com/mod/guns-without-roses), [Curseforge](https://www.curseforge.com/minecraft/mc-mods/guns-without-roses).

# Addons

If you want to make an addon or have compatibility with GWR, you can use the [Modrinth maven](https://support.modrinth.com/en/articles/8801191-modrinth-maven). I don't know if there is a better way for it with my current setup, but it works good enough for me. There are also a few tags I've noted on the Github wiki pages that I'd like you to fill if you do an addon.

Just be aware that:

* Those jars do not carry the javadoc, so browse through the code here to see it (not ideal I know).
* The version number are funky because I screwed up when making my gradle setup, in game they are `minecraftversion-modversion`, for example `1.20.1-2.3.0`, but I think on modrinth it's just the normal version number. Sorry.

You can see [Guns Without Roses Additions](https://github.com/Lykrast/GunsWithoutRosesAdditions) as an example if you want.

* Guns are instances/extend `GunItem`. There are a lot of methods to override if you want to mess with the behavior.
* Gatlings are instances/extend `GatlingItem`. This handles the fractional fire delay and the hold to fire.
* Bullets implement `IBullet`, though for most uses using/overriding `BulletItem` should be good enough. There's several methods in `IBullet` to override for more behavior (like `ExplosiveBulletItem` for example), but you may need a custom entity overriding `BulletEntity` for more complex projectile behavior (such as `PiercingBulletEntity` used by `PiercingBulletItem`).
* There are a few tags that you should fill with your new content, look over at the github wiki pages for that. They also have explainations for some stats.