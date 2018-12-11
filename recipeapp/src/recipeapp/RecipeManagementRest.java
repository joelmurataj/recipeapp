package recipeapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.recipeapp.dto.RecipeDto;
import com.recipeapp.service.RecipeService;

@RestController
public class RecipeManagementRest {

	private RecipeDto recipeDto;
	@Autowired
	private RecipeService recipeService;

	/*---Add new book---*/
	@PostMapping("/recipe")
	public ResponseEntity<?> save(@RequestBody RecipeDto recipDto) {
		if (recipeService.create(recipDto)) {
			return ResponseEntity.ok().body("New recipe has been saved with ID:" + recipeDto.getId());
		}
		else return null;
	}

	@GetMapping("/recipes")
	   public ResponseEntity<List<RecipeDto>> list() {
	      List<RecipeDto> books = recipeService.getAllRecipes();
	      return ResponseEntity.ok().body(books);
	   }
	
}
