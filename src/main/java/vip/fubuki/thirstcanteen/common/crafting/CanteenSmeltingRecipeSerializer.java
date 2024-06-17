package vip.fubuki.thirstcanteen.common.crafting;

import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class CanteenSmeltingRecipeSerializer extends SimpleCookingSerializer<CanteenSmeltingRecipe> implements RecipeSerializer<CanteenSmeltingRecipe>{

    public static CanteenSmeltingRecipeSerializer INSTANCE = new CanteenSmeltingRecipeSerializer(CanteenSmeltingRecipe::new,100);

    public CanteenSmeltingRecipeSerializer(AbstractCookingRecipe.Factory<CanteenSmeltingRecipe> pFactory, int pCookingTime) {
        super(pFactory, pCookingTime);
    }
}
