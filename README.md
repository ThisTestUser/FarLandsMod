## Far Lands Mod
A mod to restore the far lands in Forge.
This mod was made for 1.12.2, but this should work for any version.

### How does this work?
This mod works by using ASM to directly patch NoiseGeneratorOctaves.class.
In beta 1.8 and above, the following code below is used to fix the far lands:
```
k = k % 16777216L;
l = l % 16777216L;
```
By removing this function, we can therefore allow the integers to overflow and add back the far lands!

### What makes this mod different?
While there are other Far Lands mods out there, this mod uses the quickest way to add back the far lands. The fact that we don't override anything and only change 2 lines means that we could potentially create the Far Lands in **other dimensions** as well!

For example, here is the far lands in Twilight Forest.

Note that not all dimensions added support this far lands feature, as some use a completely different terrain generator.