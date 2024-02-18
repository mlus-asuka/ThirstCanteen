package vip.fubuki.thirstcanteen.common.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.fubuki.thirstcanteen.ThirstCanteen;

public class CanteenSmeltingRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CanteenSmeltingRecipe>{

    public static CanteenSmeltingRecipeSerializer INSTANCE = new CanteenSmeltingRecipeSerializer();

    @Override
    public @NotNull CanteenSmeltingRecipe fromJson(@NotNull ResourceLocation resourceLocation, @NotNull JsonObject jsonObject) {
        return new CanteenSmeltingRecipe(RecipeSerializer.SMELTING_RECIPE.fromJson(resourceLocation,jsonObject));
    }

    @Nullable
    @Override
    public CanteenSmeltingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
        try{
            SmeltingRecipe recipe = RecipeSerializer.SMELTING_RECIPE.fromNetwork(resourceLocation,friendlyByteBuf);
            if(recipe != null){
                return new CanteenSmeltingRecipe(recipe);
            }
        }catch (Exception e){
            ThirstCanteen.LOGGER.error("Error reading Thirst Canteen Recipe from Packet",e);
        }
        return null;
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, @NotNull CanteenSmeltingRecipe canteenSmeltingRecipe) {
        try{
            RecipeSerializer.SMELTING_RECIPE.toNetwork(friendlyByteBuf,canteenSmeltingRecipe);
        }catch (Exception e){
            ThirstCanteen.LOGGER.error("Error writing Thirst Canteen Recipe from Packet",e);
        }
    }
}
