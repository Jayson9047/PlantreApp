import csv
import json

BASE_URL = 'https://plantre.blob.core.windows.net/pictures/'


class Plant():
    def __init__(self, genus, species, family, order, x_class, sub_class, division, sub_division, scientific_name,
                 common_name, seed_water_rate, seedling_water_rate, mature_water_rate, min_seed_moisture,
                 max_seed_moisture, min_seedling_moisture, max_seedling_moisture, min_mature_moisture,
                 max_mature_moisture, max_harvest_day, min_harvest_day, description, pictures, created_at, updated_at):
        self.genus = genus
        self.species = species
        self.description = description
        self.family = family
        self.order = order
        self.x_class = x_class
        self.sub_class = sub_class
        self.division = division
        self.sub_division = sub_division
        self.scientific_name = scientific_name
        self.name = common_name
        self.created_at = created_at
        self.updated_at = updated_at
        self.seed_water_rate = seed_water_rate
        self.seedling_water_rate = seedling_water_rate
        self.mature_water_rate = mature_water_rate
        self.min_seed_moisture = min_seed_moisture
        self.max_seed_moisture = max_seed_moisture
        self.min_seedling_moisture = min_seedling_moisture
        self.max_seedling_moisture = max_seedling_moisture
        self.min_mature_moisture = min_mature_moisture
        self.max_mature_moisture = max_mature_moisture
        self.max_harvest_day = max_harvest_day
        self.min_harvest_day = min_harvest_day
        self.pictures = pictures


def create_csv():
    fieldnames_usda = ["", "betydb.species.id", "Genus", "Species", "ScientificName", "CommonName", "notes",
                       "created_at", "updated_at", "AcceptedSymbol", "SynonymSymbol", "Symbol", "PLANTS_Floristic_Area",
                       "State", "Category", "Family", "FamilySymbol", "FamilyCommonName", "xOrder", "SubClass", "Class",
                       "SubDivision", "Division", "SuperDivision", "SubKingdom", "Kingdom", "ITIS_TSN", "Duration",
                       "GrowthHabit", "NativeStatus", "FederalNoxiousStatus", "FederalNoxiousCommonName",
                       "StateNoxiousStatus", "StateNoxiousCommonName", "Invasive", "Federal_TE_Status",
                       "State_TE_Status", "State_TE_Common_Name", "NationalWetlandIndicatorStatus",
                       "RegionalWetlandIndicatorStatus", "ActiveGrowthPeriod", "AfterHarvestRegrowthRate", "Bloat",
                       "C2N_Ratio", "CoppicePotential", "FallConspicuous", "FireResistance", "FlowerColor",
                       "FlowerConspicuous", "FoliageColor", "FoliagePorositySummer", "FoliagePorosityWinter",
                       "FoliageTexture", "FruitColor", "FruitConspicuous", "GrowthForm", "GrowthRate", "MaxHeight20Yrs",
                       "MatureHeight", "KnownAllelopath", "LeafRetention", "Lifespan", "LowGrowingGrass",
                       "NitrogenFixation", "ResproutAbility", "Shape_and_Orientation", "Toxicity", "AdaptedCoarseSoils",
                       "AdaptedMediumSoils", "AdaptedFineSoils", "AnaerobicTolerance", "CaCO3Tolerance",
                       "ColdStratification", "DroughtTolerance", "FertilityRequirement", "FireTolerance",
                       "MinFrostFreeDays", "HedgeTolerance", "MoistureUse", "pH_Minimum", "pH_Maximum",
                       "Min_PlantingDensity", "Max_PlantingDensity", "Precipitation_Minimum", "Precipitation_Maximum",
                       "RootDepthMinimum", "SalinityTolerance", "ShadeTolerance", "TemperatureMinimum", "BloomPeriod",
                       "CommercialAvailability", "FruitSeedAbundance", "FruitSeedPeriodBegin", "FruitSeedPeriodEnd",
                       "FruitSeedPersistence", "Propogated_by_BareRoot", "Propogated_by_Bulbs",
                       "Propogated_by_Container", "Propogated_by_Corms", "Propogated_by_Cuttings", "Propogated_by_Seed",
                       "Propogated_by_Sod", "Propogated_by_Sprigs", "Propogated_by_Tubers", "Seeds_per_Pound",
                       "SeedSpreadRate", "SeedlingVigor", "SmallGrain", "VegetativeSpreadRate",
                       "Berry_Nut_Seed_Product", "ChristmasTreeProduct", "FodderProduct", "FuelwoodProduct",
                       "LumberProduct", "NavalStoreProduct", "NurseryStockProduct", "PalatableBrowseAnimal",
                       "PalatableGrazeAnimal", "PalatableHuman", "PostProduct", "ProteinPotential", "PulpwoodProduct",
                       "VeneerProduct"]
    fieldnames_plantre = ["genus", "species", "scientific_name", "name", "description", "family", "order", "sub_class",
                          "x_class", "sub_division", "division", "seed_water_rate", "seedling_water_rate",
                          "mature_water_rate", "min_seed_moisture", "max_seed_moisture", "min_seedling_moisture",
                          "max_seedling_moisture", "min_mature_moisture", "max_mature_moisture", "pictures", "created_at",
                          "updated_at"]
    try:
        with open("usdaplants.csv", "r") as usda_plants:
            with open("plantreplants.csv", 'w') as plantre_plants:
                reader = csv.DictReader(usda_plants, fieldnames=fieldnames_usda)
                createdSan = False

                next(reader, None)  # skip the headers

                writer = csv.DictWriter(plantre_plants, fieldnames=fieldnames_plantre)
                writer.writeheader()

                plants = []

                for row in reader:

                    try:
                        max_percip = float(row['Precipitation_Maximum'])
                    except ValueError as error:
                        max_percip = 0

                    try:
                        min_percip = float(row['Precipitation_Minimum'])
                    except ValueError as error:
                        min_percip = 0

                    seed_water_rate = max_percip
                    seedling_water_rate = max_percip
                    mature_water_rate = max_percip
                    min_seed_moisture = min_percip
                    max_seed_moisture = max_percip
                    min_seedling_moisture = min_percip
                    max_seedling_moisture = max_percip
                    min_mature_moisture = min_percip
                    max_mature_moisture = max_percip

                    # Setting Sensible Defaults for values not known
                    if seed_water_rate == 0:  # Seed watering rates should not be zero hours
                        seed_water_rate = 24  # Assume All seeds need to be watered every 24 hours

                    if seedling_water_rate == 0:
                        if "Tree" in row['GrowthHabit']:
                            seedling_water_rate == 48

                        if "Shrub" in row['GrowthHabit']:  # Due to fall through 'Tree/Shrub is Shrub
                            seedling_water_rate == 36

                        if "Herb" in row['GrowthHabit']:
                            seedling_water_rate == 12

                        if "Graminoid" in row['GrowthHabit']:
                            seedling_water_rate == 12

                    if mature_water_rate == 0:
                        if "Tree" in row['GrowthHabit']:
                            mature_water_rate == -1

                        if "Shrub" in row['GrowthHabit']:  # Due to fall through 'Tree/Shrub is Shrub
                            mature_water_rate == 168

                        if "Herb" in row['GrowthHabit']:
                            mature_water_rate == 48

                        if "Graminoid" in row['GrowthHabit']:
                            mature_water_rate == 48

                    # Create Converted object'

                    if "tomato" in row["CommonName"].lower() or "basil" in row["CommonName"].lower():
                        generic_pic = BASE_URL + 'generic/1.jpeg'
                        plant = Plant(row['Genus'], row['Species'], row['Family'], row['xOrder'], row['Class'],
                                      row['SubClass'], row['Division'], row['SubDivision'], row['ScientificName'],
                                      row['CommonName'], seed_water_rate, seedling_water_rate, mature_water_rate,
                                      min_seed_moisture, max_seed_moisture, min_seedling_moisture,
                                      max_seedling_moisture, min_mature_moisture, max_mature_moisture, 80, 120, row['notes'],
                                      [generic_pic], row['created_at'], row['updated_at'])
                        print(row["CommonName"])
                        print(row["ScientificName"])
                        # Handle Sweet Basil
                        if "sweet basil" in row["CommonName"].lower():
                            sweet_pic = BASE_URL + 'basil/Sweet/1.jpeg'
                            plant.pictures = [sweet_pic]
                            plant.max_harvest_day = 24
                            plant.min_harvest_day = 42
                            plant.description = "Basil is sensitive to cold, with best growth in hot, dry conditions. " \
                                                "It behaves as an annual if there is any chance of a frost. However, " \
                                                "due to its popularity, basil is cultivated in many countries around " \
                                                "the world. Production areas include countries in the Mediterranean " \
                                                "area, those in the temperate zone, and others in subtropical " \
                                                "climates." \
                                                "Basil plants require regular watering, but not as much attention as " \
                                                "is needed in other climates. If its leaves have wilted from lack of " \
                                                "water, it will recover if watered thoroughly and placed in a sunny " \
                                                "location. Yellow leaves towards the bottom of the plant are an " \
                                                "indication that the plant has been stressed; usually this means that " \
                                                "it needs less water, or less or more fertilizer. Basil can be " \
                                                "propagated reliably from cuttings with the stems of short cuttings " \
                                                "suspended in water for two weeks or until roots develop." \
                                                "Basil plants require regular watering, but not as much attention as " \
                                                "is needed in other " \
                                                "climates. If its leaves have wilted from lack of water, " \
                                                "it will recover if watered thoroughly and placed in a sunny " \
                                                "location. Yellow leaves towards the bottom of the plant are an " \
                                                "indication that the plant has been stressed; usually this means that " \
                                                "it needs less water, or less or more fertilizer. Basil can be " \
                                                "propagated reliably from cuttings with the stems of short cuttings " \
                                                "suspended in water for two weeks or until roots develop." \
                                                "Basil is one of the main ingredients in pesto, an Italian sauce with " \
                                                "olive oil and basil as its primary ingredients. It is also an " \
                                                "essential ingredient in the popular Italian-American marinara " \
                                                "sauce.[citation needed] Chinese cuisine also uses fresh or dried " \
                                                "basils in soups and other foods. In Taiwan, people add fresh basil " \
                                                "leaves to thick soups. They also eat fried chicken with deep-fried " \
                                                "basil leaves. Basil (most commonly Thai basil) is commonly steeped " \
                                                "in cream or milk to create an interesting flavor in ice cream or " \
                                                "chocolates (such as truffles). The leaves are not the only part of " \
                                                "basil used in culinary applications, the flower buds have a more " \
                                                "subtle flavor and they are edible "
                        # Handle a tomato - Sanz Marzano
                        if "garden tomato" in row["CommonName"].lower() and createdSan == False:
                            createdSan = True;
                            sweet_pic = BASE_URL + 'tomato/San Marzano/1.jpeg'
                            plant.pictures = [sweet_pic]
                            plant.max_harvest_day = 75
                            plant.min_harvest_day = 110
                            plant.name = "Tomato(San Marzano)"
                            plant.description = "The San Marzano vines are an indeterminate type vine, and have a " \
                                                "somewhat longer season than other paste tomato varieties, " \
                                                "making them particularly suitable for warmer climates. Plant the " \
                                                "tomato with at least two-thirds of the stem buried underground, " \
                                                "as planting tomatoes deeply will develop a stronger root system and " \
                                                "a healthy, more resistant plant. You can even dig a trench and bury " \
                                                "the plant sideways with the growing tip above the surface of the " \
                                                "soil. Allow at least 30 to 48 inches (approximately 1 meter) between " \
                                                "each plant. Provide a stake or tomato cage for growing San Marzano, " \
                                                "then tie up branches as the plant grows using garden twine or strips " \
                                                "of pantyhose. Water tomato plants moderately. Don’t allow the soil " \
                                                "to become either soggy or bone dry. Tomatoes are heavy feeders. " \
                                                "Side-dress the plants (sprinkle dry fertilizer next to or around the " \
                                                "plant) when the fruit is about the size of a golf ball, then repeat " \
                                                "every three weeks throughout the growing season. Water well. "

                        if "cerasiforme" in row["ScientificName"].lower():
                            cherry_pic = BASE_URL + 'tomato/Cherry/1.jpeg'
                            plant.pictures = [cherry_pic]
                            plant.name = "Cherry Tomato"
                            plant.max_harvest_day = 50
                            plant.min_harvest_day = 65
                            plant.description = "The cherry tomato is a type of small round tomato believed to be an " \
                                                "intermediate genetic admixture between wild currant-type tomatoes " \
                                                "and domesticated garden tomatoes. Cherry tomatoes range in size " \
                                                "from a thumbtip up to the size of a golf ball, and can range from " \
                                                "spherical to slightly oblong in shape. Although usually red, " \
                                                "other colours such as yellow, green, purple, and black also exist.[" \
                                                "3] Those shaped like an oblong share characteristics with plum " \
                                                "tomatoes and are known as grape tomatoes. The cherry tomato is " \
                                                "regarded as a botanical variety of the cultivated berry, " \
                                                "Solanum lycopersicum var. cerasiforme. In supermarkets, " \
                                                "cherry tomatoes of different colors are often sold together with the " \
                                                "phrase 'mixed melody' in the name, indicating the great variance in " \
                                                "their colors. "

                        plants.append(plant.__dict__)

                # Write to Plantre CSV
                with open('plantreplants.json', 'w') as jsonfile:
                    jsonfile.write(json.dumps(plants))




    except IOError as error:
        print(error)
    print("ello")


if __name__ == '__main__':
    create_csv()
