/** 
 DATE:		23-February-2022 
 * @name plant.ts 
 * @author Shaq Purcell 
 * @version 
 * @description Router for API plants
 */

import { Router } from 'express';
import PlantController from '../../controllers/PlantController';

const plantAPIRouter = Router();

plantAPIRouter.get('/', PlantController.listPlants);
export default plantAPIRouter;
