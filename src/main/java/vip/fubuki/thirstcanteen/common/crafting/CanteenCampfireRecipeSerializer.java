package vip.fubuki.thirstcanteen.common.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.fubuki.thirstcanteen.ThirstCanteen;

public class CanteenCampfireRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CanteenCampfireRecipe>{
    public static CanteenCampfireRecipeSerializer INSTANCE = new CanteenCampfireRecipeSerializer();

    @Override
    public @NotNull CanteenCampfireRecipe fromJson(@NotNull ResourceLocation resourceLocation, @NotNull JsonObject jsonObject) {
        return new CanteenCampfireRecipe(RecipeSerializer.CAMPFIRE_COOKING_RECIPE.fromJson(resourceLocation,jsonObject));
    }

    @Nullable
    @Override
    public CanteenCampfireRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
        try{
            CampfireCookingRecipe recipe = RecipeSerializer.CAMPFIRE_COOKING_RECIPE.fromNetwork(resourceLocation,friendlyByteBuf);
            if(recipe != null){
                return new CanteenCampfireRecipe(recipe);
            }
        }catch (Exception e){
            ThirstCanteen.LOGGER.error("Error reading Thirst Canteen Recipe from Packet",e);
        }
        return null;
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, @NotNull CanteenCampfireRecipe canteenRecipe) {
        try{
            RecipeSerializer.CAMPFIRE_COOKING_RECIPE.toNetwork(friendlyByteBuf,canteenRecipe);
        }catch (Exception e){
            ThirstCanteen.LOGGER.error("Error writing Thirst Canteen Recipe from Packet",e);
        }
    }
}
