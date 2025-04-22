export interface Reservation {
  id?: number; // optional for new reservations
  idStade: number;
  date: string; // LocalDate comes as ISO string (e.g., "2025-04-20")
  idPremiereEquipe: number;
  idDeuxiemeEquipe: number;
  idOrganisateur: number;
  qrCodeBase64: string;
}
