/** 
 DATE:		23-February-2022 
 * @name loadPlants.js 
 * @author Shaq Purcell 
 * @version 
 * @description Load mongo DB with plant data
 */

var connectionString = 'mongodb://localhost/PlantreAPI';

//(23/Feb/22)::(07:20:33) - Convert CSV to plant Objects to be loaded into DB
// May have to use node.js (WebApp) - not sure if can import via mongo script file

var db = connect(connectionString);

db.plant.insertMany([
  {
    title: 'Test Plant',
    scientific_name: 'Testica Plantilla',
    pictures: ["https://testurl.s3.canada-central.ca&junkDataLinkDoesNotwork","https://testurl.s3.canada-central.ca&junkDataLinkDoesNotwork2"],
    description: 'A test plant.',
    seed_water_rate: 1,
    seedling_water_rate: 3,
    mature_water_rate: 14,
    min_seed_moisture: 0.5,
    max_seed_moisture: 0.8,
    min_seedling_moisture: 0.5,
    max_seedling_moisture: 0.8,
    min_mature_moisture: 0.5,
    max_mature_moisture: 0.8,
  },
]);
