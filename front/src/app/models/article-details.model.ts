export interface ArticleDetails {
    id: number;
    title: string;
    content: string;
    createdAt: string;
    updatedAt: string;
    userId: number;
    authorUsername: string;
    topicId: number;
    topicName: string;
    comments: ArticleComment[];
}


export interface ArticleComment {
    id: number;
    content: string;
    createdAt: string;
    updatedAt: string;
    userId: number;
    authorUsername: string;
}

