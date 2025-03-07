export interface CardData {
    id: number;
    title: string;
    content: string;
    createdAt?: Date; // Optionnel : utilisé uniquement pour les articles
    authorUsername?: string; // Optionnel : utilisé uniquement pour les articles
    buttonLabel?: string; // Optionnel : utilisé uniquement pour les topics
}
