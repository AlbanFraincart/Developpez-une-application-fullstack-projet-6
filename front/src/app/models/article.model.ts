export interface Article {
    id: number;
    title: string;
    content: string;
    createdAt: string;
    updatedAt?: string;
    userId: number;
    authorUsername: string;
    topicId: number;
}
