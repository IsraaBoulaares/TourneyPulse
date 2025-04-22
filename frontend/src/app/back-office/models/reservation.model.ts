export interface Reservation {
  id: number;
  nom: string;
  date: string;
  idStade: number;
  idPremiereEquipe: number;
  idDeuxiemeEquipe: number;
  idOrganisateur: number;
  qrCodeBase64: string;
  status?: 'CONFIRMED' | 'PENDING' | 'CANCELLED'; // Optional field for frontend use only
}
