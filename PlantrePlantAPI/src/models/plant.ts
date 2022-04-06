/** 
 DATE:		23-February-2022 
 * @name plant.ts 
 * @author Shaq Purcell 
 * @version 
 * @description Defines what a plant object looks like within the application - replicates record in DB
 */
import { Schema, model } from 'mongoose';

interface Plant {
  title: string;
  common_name: string;
  scientific_name: string;
  pictures: string[];
  description: string;
  seed_water_rate: number;
  seedling_water_rate: number;
  mature_water_rate: number;
  min_seed_moisture: number;
  max_seed_moisture: number;
  min_seedling_moisture: number;
  max_seedling_moisture: number;
  min_mature_moisture: number;
  max_mature_moisture: number;
}

const schema = new Schema<Plant>({
  title: { type: String, required: true },
  common_name: { type: String, required: true },
  scientific_name: { type: String, required: true },
  pictures: { type: [String], required: true },
  description: { type: String, required: true },
  seed_water_rate: { type: Number, required: true },
  seedling_water_rate: { type: Number, required: true },
  mature_water_rate: { type: Number, required: true },
  min_seed_moisture: { type: Number, required: true },
  max_seed_moisture: { type: Number, required: true },
  min_seedling_moisture: { type: Number, required: true },
  max_seedling_moisture: { type: Number, required: true },
  min_mature_moisture: { type: Number, required: true },
  max_mature_moisture: { type: Number, required: true },
});

const PlantModel = model<Plant>('plant', schema);

export default PlantModel;
