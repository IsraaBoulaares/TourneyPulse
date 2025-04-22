export interface Equipe {
  id?: number;
  teamName: string;
  homeStadium?: string;
  foundedDate: string; // Backend sends LocalDateTime as ISO string or formatted string
  lastUpdated?: string; // Backend sends LocalDateTime as ISO string or formatted string
  status: string; // Enum TeamStatus as string (e.g., 'ACTIVE', 'INACTIVE')
  league?: string;
  teamColors?: string;
  squadSizeLimit?: number;
  players?: Users[]; // Optional, for player list if needed
}

export interface Users {
  id?: number;
  name?: string; // Adjust based on actual Users entity fields
  email?: string;
}
