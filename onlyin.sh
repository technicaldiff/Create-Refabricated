#!/usr/bin/env bash

for file in $(find src/main/java -type f -name "*.java"); do
	rm -v "$file.bak"
	sed -i'.bak' 's/@OnlyIn/@Environment/g' $file; rm -v "$file.bak"
	sed -i'.bak' 's/Dist\./EnvType./g' $file; rm -v "$file.bak"
	sed -i'.bak' 's/EnvType\.DEDICATED_SERVER/EnvType.SERVER/g' $file; rm -v "$file.bak"
	sed -i'.bak' 's/import net\.minecraftforge\.api\.distmarker\.OnlyIn;/import net.fabricmc.api.Environment;/g' $file; rm -v "$file.bak"
	sed -i'.bak' 's/import net\.minecraftforge\.api\.distmarker\.Dist;/import net.fabricmc.api.EnvType;/g' $file; rm -v "$file.bak"
done
