package ocp

import org.junit.jupiter.api.Test

import java.util.*
import kotlin.test.assertEquals

class NotificationServiceTest {

//    @Test
//    fun `get category`() {
//        val category = Category("name", "description", "imageId", "imageURL")
//
//        val categoryService = CategoryService(
//            FakeCategoryRepository(category),
//            FakeCategoryZoneRepository()
//        )
//
//        assertEquals(category,
//            categoryService.getCategory(UUID.randomUUID()))
//    }
//
//    @Test
//    fun `save category`() {
//
//        val categoryRepository = FakeCategoryRepository()
//
//        val categoryService = CategoryService(
//            categoryRepository,
//            FakeCategoryZoneRepository()
//        )
//
//        categoryService.saveCategory(CreateCategoryDto("name", "description", "imageId"))
//
//        categoryRepository.wasSaved(
//            Category(
//                "name",
//                "description",
//                "imageId",
//                "live/bucket/category/imageId"
//            )
//        )
//    }
//
//    @Test
//    fun `can untag category`() {
//        val categoryService = CategoryService(
//            FakeCategoryRepository(),
//            FakeCategoryZoneRepository()
//        )
//
//        val actual = categoryService.canUntagCategory("categoryId", "zoneId")
//
//        assertEquals(true, actual)
//    }
//
//    @Test
//    fun `cannot untag category`() {
//        val categoryService = CategoryService(
//            FakeCategoryRepository(),
//            FakeCategoryZoneRepository(1)
//        )
//
//        val actual = categoryService.canUntagCategory("categoryId", "zoneId")
//
//        assertEquals(false, actual)
//    }
//}

//class FakeCategoryZoneRepository(private val count: Int = 0) : CategoryZoneRepository {
//    override fun countChildrenByParentAndZone(categoryId: String, zoneId: String): Int = count
//
//}
//
//class FakeCategoryRepository(private val category: Category = Category("","","","")) : CategoryRepository {
//    private lateinit var savedCategory: Category
//
//    override fun findById(categoryId: UUID): Category = category
//
//    override fun save(category: Category) {
//        this.savedCategory = category
//    }
//
//    fun wasSaved(category: Category) {
//        assertEquals(category, this.savedCategory)
//    }

}
