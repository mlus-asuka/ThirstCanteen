package vip.fubuki.thirstcanteen.common.crafting;

import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class CanteenCampfireRecipeSerializer extends SimpleCookingSerializer<CanteenCampfireRecipe> implements RecipeSerializer<CanteenCampfireRecipe>{
    public static CanteenCampfireRecipeSerializer INSTANCE = new CanteenCampfireRecipeSerializer(CanteenCampfireRecipe::new,200);

    public CanteenCampfireRecipeSerializer(AbstractCookingRecipe.Factory<CanteenCampfireRecipe> pFactory, int pCookingTime) {
        super(pFactory, pCookingTime);
    }
}
