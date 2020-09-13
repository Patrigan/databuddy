package commoble.databuddy.examplecontent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import commoble.databuddy.data.MergeableJsonDataManager;
import net.minecraft.util.ResourceLocation;

public class FlavorTags
{
	public static final MergeableJsonDataManager<RawFlavorTag, Set<ResourceLocation>> DATA_LOADER = new MergeableJsonDataManager<>(
		"flavors",										// folder name
		TypeToken.get(RawFlavorTag.class).getType(),	// object type we deserialize jsons to
		new Gson(),										// json deserializer
		LogManager.getLogger(),
		FlavorTags::processFlavorTags);					// data merger/processer					
	

	
	public static Set<ResourceLocation> processFlavorTags(final Stream<RawFlavorTag> raws)
	{
		Set<ResourceLocation> set = raws.reduce(new HashSet<ResourceLocation>(), FlavorTags::processFlavorTag, Sets::union);
		
		return set;
	}
	
	public static Set<ResourceLocation> processFlavorTag(final Set<ResourceLocation> set, final RawFlavorTag raw)
	{
		return processFlavors(raw.replace ? new HashSet<>() : set, raw.values);
	}
	
	public static Set<ResourceLocation> processFlavors(final Set<ResourceLocation> set, final List<String> flavors)
	{
		for (String flavor : flavors)
		{
			set.add(new ResourceLocation(flavor));
		}
		return set;
	}
}